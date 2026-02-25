package com.smtm.pickle.presentation.ui.dialog

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
fun ShareInvitationCodeDialog(
    modifier: Modifier = Modifier,
    inviteCode: String,
    onInviteCodeClick: () -> Unit,
    onShareToSms: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    var isCopied by remember { mutableStateOf(false) }
    val tailingIcon = @Composable {
        if (isCopied) {
            Icon(
                painter = painterResource(R.drawable.ic_textfield_success),
                contentDescription = "copied",
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified,
            )
        } else {
            PickleIconButton(
                painter = painterResource(R.drawable.ic_copy),
                onClick = {
                    isCopied = true
                    onInviteCodeClick()
                },
                buttonSize = 24.dp,
                iconSize = 20.dp,
            )
        }
    }

    PickleDialog(
        modifier = modifier,
        title = stringResource(R.string.share_invitation_code_dialog_title),
        buttonLayout = PickleDialogButtonLayout.Vertical(
            primaryText = stringResource(R.string.share_invitation_code_dialog_share_sms),
            ghostText = stringResource(R.string.share_invitation_code_dialog_dismiss),
            onPrimaryClick = { onShareToSms(inviteCode) },
            onGhostClick = onDismiss,
        ),
        onDismiss = onDismiss,
        inputField = {
            PickleTextField(
                value = inviteCode,
                onValueChange = {},
                inputState = InputState.Idle,
                trailingIcon = tailingIcon
            )
        },
    )
}

@Preview
@Composable
private fun ShareInvitationCodeDialogPreview() {
    PickleTheme {
        ShareInvitationCodeDialog(
            inviteCode = "inviteCode",
            onInviteCodeClick = {},
            onShareToSms = {},
            onDismiss = {},
        )
    }
}