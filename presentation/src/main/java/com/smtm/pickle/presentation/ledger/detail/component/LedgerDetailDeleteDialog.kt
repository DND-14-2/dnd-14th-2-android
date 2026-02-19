package com.smtm.pickle.presentation.ledger.detail.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.dialog.PickleDialog
import com.smtm.pickle.presentation.designsystem.components.dialog.model.PickleDialogButtonLayout
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun LedgerDetailDeleteDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onDeleteButtonClick: () -> Unit,
) {
    PickleDialog(
        title = stringResource(R.string.ledger_detail_delete_dialog_title),
        subtitle = stringResource(R.string.ledger_detail_delete_dialog_description),
        buttonLayout = PickleDialogButtonLayout.Horizontal(
            confirmText = stringResource(R.string.common_positive),
            cancelText = stringResource(R.string.common_negative),
            onConfirmClick = onDeleteButtonClick,
            onCancelClick = onDismiss,
        ),
        onDismiss = onDismiss,
        modifier = modifier,
    )
}

@Preview(
    name = "LedgerDetailDeleteDialogPreview",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerDetailDeleteDialogPreview() {
    PickleTheme {
        LedgerDetailDeleteDialog(
            onDismiss = {},
            onDeleteButtonClick = {}
        )
    }
}
