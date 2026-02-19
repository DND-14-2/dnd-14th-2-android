package com.smtm.pickle.presentation.ui.dialog

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.designsystem.components.dialog.PickleDialog
import com.smtm.pickle.presentation.designsystem.components.dialog.model.PickleDialogButtonLayout
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
private fun ShareInviteCodeDialog(
    modifier: Modifier = Modifier,
    inviteCode: String,
    onShareToSms: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    PickleDialog(
        modifier = modifier,
        title = "친구에게 내 초대코드를\n공유해볼까요?",
        subtitle = "subtitle",
        buttonLayout = PickleDialogButtonLayout.Vertical(
            primaryText = "문자로 공유하기",
            ghostText = "괜찮아요",
            onPrimaryClick = { onShareToSms(inviteCode) },
            onGhostClick = onDismiss,
        ),
        onDismiss = onDismiss,
        inputField = {
            Text(text = inviteCode)
        },
    )
}

@Preview
@Composable
private fun ShareInviteCodeDialogPreview() {
    PickleTheme {
        ShareInviteCodeDialog(
            inviteCode = "inviteCode",
            onShareToSms = {},
            onDismiss = {},
        )
    }
}