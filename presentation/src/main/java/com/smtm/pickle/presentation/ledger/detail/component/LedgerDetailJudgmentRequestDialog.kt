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
fun LedgerDetailJudgmentRequestDialog(
    modifier: Modifier = Modifier,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    PickleDialog(
        title = stringResource(id = R.string.ledger_detail_request_dialog_title),
        subtitle = stringResource(id = R.string.ledger_detail_request_dialog_subtitle),
        buttonLayout = PickleDialogButtonLayout.Horizontal(
            confirmText = stringResource(id = R.string.ledger_detail_request_dialog_confirm),
            cancelText = stringResource(id = R.string.common_cancel),
            onConfirmClick = onConfirmClick,
            onCancelClick = onCancelClick,
        ),
        onDismiss = onDismiss,
        modifier = modifier,
        imageRes = R.drawable.illust_mypage_balances,
    )
}

@Preview
@Composable
private fun LedgerDetailJudgmentRequestDialogPreview() {
    PickleTheme {
        LedgerDetailJudgmentRequestDialog(
            onConfirmClick = {},
            onCancelClick = {},
            onDismiss = {},
        )
    }
}
