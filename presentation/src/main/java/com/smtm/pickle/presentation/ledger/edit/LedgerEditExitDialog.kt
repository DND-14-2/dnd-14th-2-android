package com.smtm.pickle.presentation.ledger.edit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.PickleDialog
import com.smtm.pickle.presentation.designsystem.components.button.PickleButtonGroup
import com.smtm.pickle.presentation.designsystem.components.button.PickleButtonV2
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonGroupLayout
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonSize
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonType
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun LedgerEditExitDialog(
    modifier: Modifier = Modifier,
    onContinueEdit: () -> Unit,
    onConfirmExit: () -> Unit,
) {
    PickleDialog(
        modifier = modifier,
        onDismiss = onContinueEdit,
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 40.dp, bottom = 20.dp),
    ) {
        Text(
            text = stringResource(R.string.ledger_edit_exit_dialog_title),
            style = PickleTheme.typography.head3Bold,
            color = PickleTheme.colors.gray800
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.ledger_edit_exit_dialog_description),
            style = PickleTheme.typography.body2Medium,
            color = PickleTheme.colors.gray600
        )

        Spacer(modifier = Modifier.height(30.dp))

        PickleButtonGroup(
            modifier = Modifier.fillMaxWidth(),
            layout = PickleButtonGroupLayout.RowEqual,
            buttonSize = PickleButtonSize.Large,
            leadingButton = { modifier, buttonSize ->
                PickleButtonV2(
                    modifier = modifier,
                    text = stringResource(R.string.ledger_edit_exit_dialog_negative),
                    onClick = onConfirmExit,
                    type = PickleButtonType.Secondary,
                    size = buttonSize,
                )
            },
            trailingButton = { modifier, buttonSize ->
                PickleButtonV2(
                    modifier = modifier,
                    text = stringResource(R.string.ledger_edit_exit_dialog_positive),
                    onClick = onContinueEdit,
                    type = PickleButtonType.Primary,
                    size = buttonSize,
                )
            },
        )
    }
}

@Preview(
    name = "Ledger Edit Exit Dialog",
    widthDp = 360,
    showBackground = true,
)
@Composable
private fun LedgerEditExitDialogPreview() {
    PickleTheme {
        LedgerEditExitDialog(
            onContinueEdit = {},
            onConfirmExit = {}
        )
    }
}
