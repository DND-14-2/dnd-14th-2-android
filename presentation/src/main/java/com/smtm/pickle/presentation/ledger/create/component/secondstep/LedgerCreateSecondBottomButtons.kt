package com.smtm.pickle.presentation.ledger.create.component.secondstep

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.PickleButtonGroup
import com.smtm.pickle.presentation.designsystem.components.button.PickleButtonV2
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonLayout
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonSize
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonType
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun LedgerCreateSecondBottomButtons(
    modifier: Modifier = Modifier,
    enabledSuccess: Boolean,
    onPreviousClick: () -> Unit,
    onSuccessClick: () -> Unit,
) {
    PickleButtonGroup(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 14.dp),
        layout = PickleButtonLayout.RowFixedLeading,
        buttonSize = PickleButtonSize.Large,
        leadingButton = { modifier, buttonSize ->
            PickleButtonV2(
                modifier = modifier,
                text = stringResource(R.string.common_previous),
                onClick = onPreviousClick,
                type = PickleButtonType.Secondary,
                size = buttonSize,
            )
        },
        trailingButton = { modifier, buttonSize ->
            PickleButtonV2(
                modifier = modifier,
                text = stringResource(R.string.ledger_create_success),
                onClick = onSuccessClick,
                enabled = enabledSuccess,
                size = buttonSize,
            )
        },
    )
}

@Preview(
    name = "LedgerCreateSecondBottomButtonsPreview - Disable",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateSecondBottomButtonsDisablePreview() {
    PickleTheme {
        LedgerCreateSecondBottomButtons(
            enabledSuccess = false,
            onPreviousClick = {},
            onSuccessClick = {}
        )
    }
}

@Preview(
    name = "LedgerCreateSecondBottomButtonsPreview - Enable",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateSecondBottomButtonsEnablePreview() {
    PickleTheme {
        LedgerCreateSecondBottomButtons(
            enabledSuccess = true,
            onPreviousClick = {},
            onSuccessClick = {}
        )
    }
}