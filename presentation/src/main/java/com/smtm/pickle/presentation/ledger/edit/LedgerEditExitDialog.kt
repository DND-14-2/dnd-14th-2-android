package com.smtm.pickle.presentation.ledger.edit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.dialog.PickleDialog
import com.smtm.pickle.presentation.designsystem.components.dialog.model.PickleDialogButtonLayout
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun LedgerEditExitDialog(
    modifier: Modifier = Modifier,
    onContinueEdit: () -> Unit,
    onConfirmExit: () -> Unit,
) {
    PickleDialog(
        title = stringResource(R.string.ledger_edit_exit_dialog_title),
        subtitle = stringResource(R.string.ledger_edit_exit_dialog_description),
        buttonLayout = PickleDialogButtonLayout.Horizontal(
            confirmText = stringResource(R.string.ledger_edit_exit_dialog_positive),
            cancelText = stringResource(R.string.ledger_edit_exit_dialog_negative),
            onConfirmClick = onContinueEdit,
            onCancelClick = onConfirmExit,
        ),
        onDismiss = onContinueEdit,
        modifier = modifier,
    )
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
