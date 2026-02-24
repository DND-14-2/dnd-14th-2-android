package com.smtm.pickle.presentation.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.PickleSupportingText
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButton
import com.smtm.pickle.presentation.designsystem.components.dialog.PickleDialog
import com.smtm.pickle.presentation.designsystem.components.dialog.model.PickleDialogButtonLayout
import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun InputInvitationCodeDialog(
    invitationCode: String,
    modifier: Modifier = Modifier,
    invitationCodeErrorMessage: String? = null,
    onInvitationCodeChange: (String) -> Unit,
    onSuccessClick: (String) -> Unit,
    onCancelClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    var inputErrorMessage by remember { mutableStateOf<String?>(null) }
    val displayError = inputErrorMessage ?: invitationCodeErrorMessage

    PickleDialog(
        modifier = modifier,
        title = "초대코드를 입력해주세요",
        buttonLayout = PickleDialogButtonLayout.Horizontal(
            confirmText = "완료",
            cancelText = "취소",
            onConfirmClick = {
                val error = when {
                    invitationCode.length < 6 -> "6자로 입력해주세요"
                    invitationCode.any { !it.isLetter() || !it.isUpperCase() } -> "영어 대문자로 입력해주세요"
                    else -> null
                }

                if (error != null) {
                    inputErrorMessage = error
                } else {
                    inputErrorMessage = null
                    onSuccessClick(invitationCode)
                }
            },
            onCancelClick = onCancelClick,
        ),
        onDismiss = onDismiss,
        inputField = {
            InvitationCodeText(
                code = invitationCode,
                onCodeChange = { newCode ->
                    inputErrorMessage = null
                    onInvitationCodeChange(newCode)
                },
                errorMessage = displayError,
            )
        }
    )
}

@Composable
private fun InvitationCodeText(
    code: String,
    onCodeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val state = when {
        errorMessage != null -> InputState.Error(errorMessage)
        code.isEmpty() -> InputState.Idle
        else -> InputState.Success(null)
    }

    val borderColor = when (state) {
        is InputState.Error -> PickleTheme.colors.error50
        is InputState.Success -> PickleTheme.colors.primary400
        InputState.Idle -> Color.Transparent
    }
    val borderWidth = if (state is InputState.Idle) 0.dp else 1.5.dp

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.inputHeight)
                .clip(RoundedCornerShape(Dimensions.radius))
                .background(PickleTheme.colors.gray50)
                .border(borderWidth, borderColor, RoundedCornerShape(Dimensions.radius)),
            contentAlignment = Alignment.CenterStart,
        ) {
            BasicTextField(
                value = code,
                onValueChange = { newValue -> onCodeChange(newValue.take(6)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = PickleTheme.typography.body4Medium.copy(
                    color = PickleTheme.colors.gray800,
                ),
                cursorBrush = SolidColor(
                    if (state is InputState.Error) PickleTheme.colors.error50
                    else PickleTheme.colors.primary400
                ),
                interactionSource = interactionSource,
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            if (code.isEmpty()) {
                                Text(
                                    text = "초대코드를 입력하세요",
                                    style = PickleTheme.typography.body4Medium,
                                    color = PickleTheme.colors.gray500,
                                )
                            }
                            innerTextField()
                        }
                        if (code.isNotEmpty()) {
                            when (state) {
                                is InputState.Error -> Icon(
                                    painter = painterResource(R.drawable.ic_snackbar_fail),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Unspecified,
                                )

                                is InputState.Success -> PickleIconButton(
                                    painter = painterResource(R.drawable.ic_description_close),
                                    onClick = { onCodeChange("") },
                                    buttonSize = 24.dp,
                                    iconSize = 20.dp
                                )

                                InputState.Idle -> Unit
                            }
                        }
                    }
                },
            )
        }
        if (state is InputState.Error) {
            Spacer(Modifier.height(10.dp))
            PickleSupportingText(message = state.message, inputState = state)
        }
    }
}

@Preview(
    name = "InputInvitationCodeDialog - Idle",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun InputInvitationCodeDialogIdlePreview() {
    PickleTheme {
        InputInvitationCodeDialog(
            invitationCode = "",
            onInvitationCodeChange = {},
            onSuccessClick = {},
            onCancelClick = {},
            onDismiss = {},
        )
    }
}

@Preview(
    name = "InputInvitationCodeDialog - Typing",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun InputInvitationCodeDialogTypingPreview() {
    PickleTheme {
        InputInvitationCodeDialog(
            invitationCode = "ABC",
            onInvitationCodeChange = {},
            onSuccessClick = {},
            onCancelClick = {},
            onDismiss = {},
        )
    }
}

@Preview(
    name = "InputInvitationCodeDialog - Error (short)",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun InputInvitationCodeDialogErrorShortPreview() {
    PickleTheme {
        InputInvitationCodeDialog(
            invitationCode = "AB",
            invitationCodeErrorMessage = "6자로 입력해주세요",
            onInvitationCodeChange = {},
            onSuccessClick = {},
            onCancelClick = {},
            onDismiss = {},
        )
    }
}

@Preview(
    name = "InputInvitationCodeDialog - Error (expired)",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun InputInvitationCodeDialogErrorExpiredPreview() {
    PickleTheme {
        InputInvitationCodeDialog(
            invitationCode = "ABCDEF",
            invitationCodeErrorMessage = "만료된 초대코드입니다",
            onInvitationCodeChange = {},
            onSuccessClick = {},
            onCancelClick = {},
            onDismiss = {},
        )
    }
}

@Preview(
    name = "InputInvitationCodeDialog - Success",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun InputInvitationCodeDialogSuccessPreview() {
    PickleTheme {
        InputInvitationCodeDialog(
            invitationCode = "ABCDEF",
            onInvitationCodeChange = {},
            onSuccessClick = {},
            onCancelClick = {},
            onDismiss = {},
        )
    }
}
