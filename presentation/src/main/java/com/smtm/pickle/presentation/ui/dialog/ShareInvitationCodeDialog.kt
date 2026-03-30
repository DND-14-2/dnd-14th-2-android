package com.smtm.pickle.presentation.ui.dialog

import android.content.ClipData
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButton
import com.smtm.pickle.presentation.designsystem.components.dialog.PickleDialog
import com.smtm.pickle.presentation.designsystem.components.dialog.model.PickleDialogButtonLayout
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import kotlinx.coroutines.launch

@Composable
fun ShareInvitationCodeDialog(
    modifier: Modifier = Modifier,
    invitationCode: String,
    onPrimaryClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val clipboardManager = LocalClipboard.current

    var isCopied by remember { mutableStateOf(false) }
    val trailingIcon = @Composable {
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
                    scope.launch {
                        val clipData = ClipData.newPlainText("invitationCode", invitationCode)
                        clipboardManager.setClipEntry(ClipEntry(clipData))
                    }
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
            onPrimaryClick = onPrimaryClick,
            onGhostClick = onDismiss,
        ),
        onDismiss = onDismiss,
        inputField = {
            InvitationCodeText(
                value = invitationCode,
                trailingIcon = trailingIcon,
            )
        },
    )
}

@Composable
private fun InvitationCodeText(
    value: String,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .height(Dimensions.inputHeight)
            .fillMaxWidth()
            .background(
                color = PickleTheme.colors.gray50,
                shape = RoundedCornerShape(Dimensions.radius)
            )
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f),
                style = PickleTheme.typography.body3Regular,
                color = PickleTheme.colors.gray800,
            )

            Spacer(modifier = Modifier.width(4.dp))

            trailingIcon()
        }
    }
}

@Preview
@Composable
private fun ShareInvitationCodeDialogPreview() {
    PickleTheme {
        ShareInvitationCodeDialog(
            invitationCode = "ABCDEF",
            onPrimaryClick = {},
            onDismiss = {},
        )
    }
}