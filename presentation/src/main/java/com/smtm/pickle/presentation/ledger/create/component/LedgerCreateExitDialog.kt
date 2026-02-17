package com.smtm.pickle.presentation.ledger.create.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.dialog.PickleDialog
import com.smtm.pickle.presentation.designsystem.components.dialog.model.PickleDialogButtonLayout
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun LedgerCreateExitDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onExitButtonClick: () -> Unit,
) {
    PickleDialog(
        title = stringResource(R.string.ledger_create_exit_dialog_title),
        subtitle = stringResource(R.string.ledger_create_exit_dialog_description),
        buttonLayout = PickleDialogButtonLayout.Horizontal(
            confirmText = stringResource(R.string.ledger_create_exit_dialog_positive),
            cancelText = stringResource(R.string.ledger_create_exit_dialog_negative),
            onConfirmClick = onDismiss,
            onCancelClick = onExitButtonClick,
        ),
        onDismiss = onDismiss,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun LedgerCreateExitDialogPreview() {
    PickleTheme {
        LedgerCreateExitDialog(
            onDismiss = {},
            onExitButtonClick = {}
        )
    }
}
