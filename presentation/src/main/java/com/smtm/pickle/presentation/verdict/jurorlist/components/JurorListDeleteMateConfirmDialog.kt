package com.smtm.pickle.presentation.verdict.jurorlist.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.dialog.PickleDialog
import com.smtm.pickle.presentation.designsystem.components.dialog.model.PickleDialogButtonLayout
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun JurorListDeleteMateConfirmDialog(
    onConfirmClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    PickleDialog(
        title = stringResource(id = R.string.juror_list_delete_mate_title),
        subtitle = stringResource(id = R.string.juror_list_delete_mate_subtitle),
        buttonLayout = PickleDialogButtonLayout.Horizontal(
            confirmText = stringResource(id = R.string.juror_list_reject),
            cancelText = stringResource(id = R.string.common_cancel),
            onConfirmClick = onConfirmClick,
            onCancelClick = onDismiss
        ),
        onDismiss = onDismiss
    )
}

@Preview
@Composable
fun JurorListDeleteMateConfirmDialogPreview() {
    PickleTheme {
        JurorListDeleteMateConfirmDialog(onConfirmClick = {}, onDismiss = {})
    }
}
