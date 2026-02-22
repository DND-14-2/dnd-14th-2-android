package com.smtm.pickle.presentation.ui.dialog

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.designsystem.components.dialog.PickleDialog
import com.smtm.pickle.presentation.designsystem.components.dialog.model.PickleDialogButtonLayout
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun InputInviteCodeDialog(
    modifier: Modifier = Modifier,
    inviteCode: String,
    onInviteCodeChange: (String) -> Unit,
    onSuccessClick: (String) -> Unit,
    onCancelClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    PickleDialog(
        modifier = modifier,
        title = "초대코드를 입력해주세요",
        buttonLayout = PickleDialogButtonLayout.Horizontal(
            confirmText = "완료",
            cancelText = "취소",
            onConfirmClick = { onSuccessClick(inviteCode) },
            onCancelClick = onCancelClick,
        ),
        onDismiss = onDismiss,
        inputField = {
            TextField(
                value = inviteCode,
                onValueChange = onInviteCodeChange,
                label = { Text(text = "내용을 입력하세요") }
            )
        }
    )
}

@Preview(
    name = "InputInviteCodeDialog",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun InputInviteCodeDialogPreview() {
    PickleTheme {
        InputInviteCodeDialog(
            inviteCode = "",
            onInviteCodeChange = {},
            onSuccessClick = {},
            onCancelClick = {},
            onDismiss = {},
        )
    }
}