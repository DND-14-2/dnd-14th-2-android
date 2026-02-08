package com.smtm.pickle.presentation.ledger.create.component.firststep

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButton
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import com.smtm.pickle.presentation.ledger.create.component.LedgerCreateHeaderText

private const val DESCRIPTION_MAX_LENGTH = 15

@Composable
fun LedgerDescriptionInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 20.dp),
    ) {
        LedgerCreateHeaderText(
            text = stringResource(R.string.ledger_create_description_header),
        )

        Spacer(modifier = Modifier.height(10.dp))

        BasicTextField(
            modifier = Modifier
                .height(Dimensions.inputHeight)
                .then(
                    if (isFocused) {
                        Modifier.border(
                            shape = RoundedCornerShape(Dimensions.radius),
                            width = 1.5.dp,
                            color = PickleTheme.colors.primary400
                        )
                    } else {
                        Modifier
                    }
                )
                .background(
                    PickleTheme.colors.gray50,
                    shape = RoundedCornerShape(Dimensions.radius),
                )
                .onFocusChanged { isFocused = it.isFocused },
            value = value,
            textStyle = PickleTheme.typography.body3Regular.copy(color = PickleTheme.colors.gray800),
            onValueChange = { value ->
                if (value.length <= DESCRIPTION_MAX_LENGTH) {
                    onValueChange(value)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus(force = true) }
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = stringResource(R.string.ledger_create_description_hint),
                                style = PickleTheme.typography.body3Regular,
                                color = PickleTheme.colors.gray600,
                            )
                        }
                        innerTextField()
                    }
                    if (value.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(4.dp))

                        PickleIconButton(
                            painter = painterResource(R.drawable.ic_description_close),
                            onClick = { onValueChange("") },
                            iconSize = 20.dp,
                            buttonSize = 24.dp
                        )
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview(
    name = "LedgerDescriptionInputFiledPreview - Value Empty",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerDescriptionInputFieldEmptyPreview() {
    PickleTheme {
        LedgerDescriptionInputField(
            value = "",
            onValueChange = {}
        )
    }
}

@Preview(
    name = "LedgerDescriptionInputFiledPreview - Value Non Empty",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerDescriptionInputFieldPreview() {
    PickleTheme {
        LedgerDescriptionInputField(
            value = "가나다라마바사아자차카타파하.",
            onValueChange = {}
        )
    }
}
