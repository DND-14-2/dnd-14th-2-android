package com.smtm.pickle.presentation.ledger.create.component.firststep

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
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

    val borderColor = if (value.isEmpty()) {
        PickleTheme.colors.transparent
    } else {
        PickleTheme.colors.primary400
    }
    val borderWidth = if (value.isEmpty()) 0.dp else 1.5.dp

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
                .border(
                    shape = RoundedCornerShape(Dimensions.radius),
                    width = borderWidth,
                    color = borderColor
                )
                .clip(RoundedCornerShape(Dimensions.radius))
                .background(PickleTheme.colors.gray50)
                .padding(horizontal = 16.dp, vertical = 13.dp),
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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
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
