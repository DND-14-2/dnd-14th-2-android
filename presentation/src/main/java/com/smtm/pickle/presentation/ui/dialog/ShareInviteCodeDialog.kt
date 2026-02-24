package com.smtm.pickle.presentation.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButton
import com.smtm.pickle.presentation.designsystem.components.dialog.PickleDialog
import com.smtm.pickle.presentation.designsystem.components.dialog.model.PickleDialogButtonLayout
import com.smtm.pickle.presentation.designsystem.components.textfield.PickleTextField
import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun ShareInviteCodeDialog(
    modifier: Modifier = Modifier,
    inviteCode: String,
    onInviteCodeClick: () -> Unit,
    onShareToSms: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    PickleDialog(
        modifier = modifier,
        title = "친구에게 내 초대코드를\n공유해볼까요?",
        buttonLayout = PickleDialogButtonLayout.Vertical(
            primaryText = "문자로 공유하기",
            ghostText = "괜찮아요",
            onPrimaryClick = { onShareToSms(inviteCode) },
            onGhostClick = onDismiss,
        ),
        onDismiss = onDismiss,
        inputField = {
            PickleTextField(
                value = inviteCode,
                onValueChange = {},
                inputState = InputState.Idle,
                trailingIcon = {
                    PickleIconButton(
                        painter = painterResource(R.drawable.ic_copy),
                        onClick = onInviteCodeClick,
                        buttonSize = 24.dp,
                        iconSize = 20.dp,
                    )
                }
            )
        },
    )
}

@Preview
@Composable
private fun ShareInviteCodeDialogPreview() {
    PickleTheme {
        ShareInviteCodeDialog(
            inviteCode = "inviteCode",
            onInviteCodeClick = {},
            onShareToSms = {},
            onDismiss = {},
        )
    }
}