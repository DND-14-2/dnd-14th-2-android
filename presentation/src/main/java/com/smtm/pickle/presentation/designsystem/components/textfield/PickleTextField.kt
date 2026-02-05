package com.smtm.pickle.presentation.designsystem.components.textfield

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.PickleSupportingText
import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState
import com.smtm.pickle.presentation.designsystem.components.textfield.model.KeyboardLocale
import com.smtm.pickle.presentation.designsystem.components.textfield.model.toUiState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

/**
 * 기본 디자인이 적용된 텍스트 필드
 * @param modifier
 * @param value 텍스트 필드 입력 값
 * @param onValueChange 텍스트 필드 입력 값 변경
 * @param height 텍스트 필드 높이
 * @param enabled 텍스트 필드 활성화 여부
 * @param readOnly 텍스트 필드 읽기 전용 여부
 * @param hint 텍스트 필드 힌트
 * @param leadingIcon 텍스트 필드 앞쪽 아이콘
 * @param trailingIcon 텍스트 필드 뒤쪽 아이콘
 * @param isSingleLine 단일 라인 입력 여부
 * @param minLines 최소 라인 수
 * @param inputState 입력 값 처리 상태
 * @param keyboardType 키보드 타입(텍스트, 이메일 등)
 * @param imeAction 키보드 액션 버튼
 * @param locale 소프트 키보드 노출 시 언어
 */
@Composable
fun PickleTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    inputState: InputState,
    height: Dp = Dimensions.inputHeight,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isSingleLine: Boolean = true,
    minLines: Int = 1,
    hint: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    decoration: @Composable ((innerTextField: @Composable () -> Unit) -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: (() -> Unit)? = null,
    locale: KeyboardLocale = KeyboardLocale.KOREA,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current
    val colors = PickleTheme.colors

    val textFieldColor = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colors.transparent,
        unfocusedBorderColor = colors.transparent,
        errorBorderColor = colors.transparent,

        focusedContainerColor = colors.gray50,
        unfocusedContainerColor = colors.gray50,
        errorContainerColor = colors.gray50,
    )

    val borderColor = remember(inputState, isFocused) {
        when {
            inputState is InputState.Error -> colors.error50
            inputState is InputState.Success && !isFocused -> colors.primary400
            isFocused -> colors.primary400
            else -> colors.transparent
        }
    }

    val borderWidth = remember(borderColor) {
        if (borderColor == colors.transparent) 0.dp else 2.dp
    }

    val heightModifier = remember(isSingleLine, height) {
        if (isSingleLine) Modifier.height(height) else Modifier
    }

    Box(
        modifier = modifier
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(Dimensions.radius),
            )
            .padding(1.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .then(heightModifier)
                .fillMaxWidth(),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = PickleTheme.typography.body4Medium.copy(
                color = PickleTheme.colors.gray800,
            ),
            cursorBrush = SolidColor(
                if (inputState is InputState.Error)
                    PickleTheme.colors.error50
                else
                    PickleTheme.colors.primary400
            ),
            decorationBox = decoration ?: @Composable { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = value,
                    visualTransformation = VisualTransformation.None,
                    innerTextField = innerTextField,
                    placeholder = {
                        Text(
                            text = hint,
                            style = PickleTheme.typography.body4Medium,
                            color = PickleTheme.colors.gray500,
                        )
                    },
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    singleLine = isSingleLine,
                    enabled = enabled,
                    isError = inputState is InputState.Error,
                    interactionSource = interactionSource,
                    colors = textFieldColor,
                    contentPadding = OutlinedTextFieldDefaults.contentPadding(
                        top = 0.dp,
                        bottom = 0.dp,
                    ),
                    container = {
                        OutlinedTextFieldDefaults.Container(
                            enabled = enabled,
                            isError = inputState is InputState.Error,
                            interactionSource = interactionSource,
                            shape = RoundedCornerShape(Dimensions.radius),
                            colors = textFieldColor
                        )
                    },
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                imeAction = imeAction,
                hintLocales = LocaleList(Locale(locale.language))
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onImeAction?.invoke()
                    focusManager.clearFocus()
                },
                onSearch = {
                    onImeAction?.invoke()
                    focusManager.clearFocus()
                },
                onSend = {
                    onImeAction?.invoke()
                    focusManager.clearFocus()
                },
                onNext = {
                    onImeAction?.invoke()
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            singleLine = isSingleLine,
            minLines = minLines,
            maxLines = if (isSingleLine) 1 else 8, // TODO: maxLine 조절 필요
        )
    }
}

/**
 * 서포팅 텍스트를 포함한 텍스트 필드
 * @param inputState 입력 값 처리 상태
 * @param value 텍스트 필드 입력 값
 * @param onValueChange 텍스트 필드 입력 값 변경
 * @param modifier
 * @param hint 텍스트 필드 힌트
 * @param trailingIcon 텍스트 필드 뒤쪽 아이콘
 * @param defaultSupportingText 기본 서포팅 텍스트
 * ---
 * ###### 예시 코드
 * ```
 * // EmailViewModel.kt
 * @HiltViewModel
 * class EmailViewModel @Inject constructor() : ViewModel() {
 *
 *     private val _email = MutableStateFlow("")
 *     val email: StateFlow<String> = _email
 *
 *     private val _emailInputState = MutableStateFlow<InputState>(InputState.Idle)
 *     val emailInputState: StateFlow<InputState> = _emailInputState
 *
 *     fun onEmailChange(value: String) {
 *         _email.value = value
 *         _emailInputState.value = validateEmail(value)
 *     }
 *
 *     private fun validateEmail(value: String): InputState {
 *         if (value.isBlank()) return InputState.Idle
 *
 *         if (!value.contains("@")) return InputState.Error("이메일 형식이 올바르지 않습니다")
 *
 *         if (value.length > 30) return InputState.Error("이메일이 너무 깁니다")
 *
 *         return InputState.Success("사용 가능한 이메일입니다")
 *     }
 * }
 * ```
 * ```
 * // EmailScreen.kt
 * @Composable
 * fun EmailScreen(
 *     viewModel: EmailViewModel = hiltViewModel()
 * ) {
 *     val email by viewModel.email.collectAsState()
 *     val inputState by viewModel.emailInputState.collectAsState()
 *
 *     PickleTextFieldWithSupporting(
 *         value = email,
 *         onValueChange = viewModel::onEmailChange,
 *         inputState = inputState,
 *         hint = "이메일",
 *         defaultSupportingText = "이메일을 입력해주세요"
 *     )
 * }
 * ```
 */
@Composable
fun PickleTextFieldWithSupporting(
    inputState: InputState,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    defaultSupportingText: String? = null,
) {
    val supportingText = inputState.toUiState(defaultSupportingText)

    Column(modifier = modifier.fillMaxWidth()) {
        PickleTextField(
            value = value,
            onValueChange = onValueChange,
            hint = hint,
            trailingIcon = trailingIcon,
            inputState = inputState,
            imeAction = imeAction,
            onImeAction = onImeAction
        )

        supportingText?.let {
            Spacer(modifier = Modifier.height(4.dp))

            Row {
                Spacer(modifier = Modifier.width(8.dp))
                PickleSupportingText(
                    message = it,
                    inputState = inputState,
                )
            }
        }
    }
}

/** PickleTextField 헬퍼 */
object PickleTextField {

    /** 상호작용이 없는 텍스트 필드 */
    @Composable
    fun Static(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        readOnly: Boolean = false,
        hint: String = "",
        leadingIcon: @Composable (() -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
        keyboardType: KeyboardType = KeyboardType.Text,
        imeAction: ImeAction = ImeAction.Done,
        onImeAction: (() -> Unit)? = null,
        locale: KeyboardLocale = KeyboardLocale.KOREA
    ) {
        PickleTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            hint = hint,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            keyboardType = keyboardType,
            inputState = InputState.Idle,
            imeAction = imeAction,
            onImeAction = onImeAction,
            locale = locale,
        )
    }

    /** 상호작용이 있는 텍스트 필드 */
    @Composable
    fun Interactive(
        value: String,
        onValueChange: (String) -> Unit,
        inputState: InputState,
        modifier: Modifier = Modifier,
        hint: String = "",
        enabled: Boolean = true,
        readOnly: Boolean = false,
        leadingIcon: @Composable (() -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
        imeAction: ImeAction = ImeAction.Done,
        onImeAction: (() -> Unit)? = null,
        keyboardType: KeyboardType = KeyboardType.Text,
        locale: KeyboardLocale = KeyboardLocale.KOREA
    ) {
        PickleTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            inputState = inputState,
            enabled = enabled,
            readOnly = readOnly,
            hint = hint,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            keyboardType = keyboardType,
            imeAction = imeAction,
            onImeAction = onImeAction,
            locale = locale,
        )
    }

    @Composable
    fun MultiLine(
        value: String,
        onValueChange: (String) -> Unit,
        inputState: InputState = InputState.Idle,
        maxCount: Int,
        modifier: Modifier = Modifier,
        readOnly: Boolean = false,
        hint: String = "",
        minLines: Int = 4,
        onDone: (() -> Unit)? = null,
        locale: KeyboardLocale = KeyboardLocale.KOREA
    ) {
        Box(modifier = modifier) {
            PickleTextField(
                modifier = Modifier,
                value = value,
                onValueChange = { newValue ->
                    if (newValue.length <= maxCount) onValueChange(newValue)
                },
                readOnly = readOnly,
                hint = hint,
                isSingleLine = false,
                minLines = minLines,
                keyboardType = KeyboardType.Text,
                inputState = inputState,
                imeAction = ImeAction.Done,
                onImeAction = onDone,
                locale = locale,
                decoration = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = PickleTheme.colors.gray50,
                                shape = RoundedCornerShape(Dimensions.radius)
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 12.dp,
                                    end = 12.dp,
                                    top = 12.dp,
                                    bottom = 32.dp
                                )
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = hint,
                                    style = PickleTheme.typography.body4Medium,
                                    color = PickleTheme.colors.gray500
                                )
                            }
                            innerTextField()
                        }

                        Text(
                            text = "${value.length}/$maxCount",
                            style = PickleTheme.typography.caption1Medium,
                            color = if (inputState is InputState.Error)
                                PickleTheme.colors.error50
                            else
                                PickleTheme.colors.gray500,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 12.dp, bottom = 8.dp)
                        )
                    }
                }
            )
        }
    }

    @Composable
    fun Search(
        modifier: Modifier = Modifier,
        value: String,
        onValueChange: (String) -> Unit,
        height: Dp = Dimensions.searchHeight,
        hint: String = "",
        onSearch: (() -> Unit)? = null
    ) {
        val leadingIcon = remember {
            @Composable {
                Image(
                    painter = painterResource(id = R.drawable.ic_search_magnifier),
                    contentDescription = null,
                )
            }
        }

        val hasText = value.isNotEmpty()
        val trailingIcon = remember(hasText) {
            if (hasText) {
                @Composable {
                    IconButton(onClick = { onValueChange("") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search_close),
                            contentDescription = null,
                            modifier = Modifier.size(Dimensions.iconMedium)
                        )
                    }
                }
            } else {
                null
            }
        }

        PickleTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            height = height,
            hint = hint,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            keyboardType = KeyboardType.Text,
            inputState = InputState.Idle,
            imeAction = ImeAction.Search,
            onImeAction = onSearch,
        )
    }
}

@Preview
@Composable
private fun EmptyTextField() {
    PickleTextField.Static(
        value = "",
        onValueChange = {}
    )
}

@Preview
@Composable
private fun TextFieldWithValue() {
    var email by remember { mutableStateOf("test@test.com") }

    PickleTextField.Interactive(
        value = email,
        onValueChange = { email = it },
        hint = "텍스트를 입력해주세요",
        inputState = InputState.Error("에러입니다")
    )
}

@Preview
@Composable
private fun TextFieldWithSupporting() {
    var email by remember { mutableStateOf("") }

    PickleTextFieldWithSupporting(
        value = email,
        onValueChange = { email = it },
        hint = "이메일",
        defaultSupportingText = "이메일을 입력해주세요",
        inputState = when {
            email == "" -> InputState.Idle
            email.length > 10 -> InputState.Error("이메일을 다시 확인해주세요")
            !email.contains("@") -> InputState.Error("이메일 형식이 올바르지 않습니다")
            email.contains("@") -> InputState.Success(null)
            else -> InputState.Error("이메일을 다시 확인해주세요")
        },
    )
}

@Preview
@Composable
private fun TextFieldWithSearch() {
    var searchValue by remember { mutableStateOf("aaa") }

    PickleTextField.Search(
        value = searchValue,
        onValueChange = { searchValue = it },
        hint = "검색어를 입력해주세요"
    )
}
