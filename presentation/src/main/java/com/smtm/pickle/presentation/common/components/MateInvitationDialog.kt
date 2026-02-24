package com.smtm.pickle.presentation.common.components

import android.content.ClipData
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.PickleIconButtonWithTouchCustom
import com.smtm.pickle.presentation.designsystem.components.dialog.PickleDialog
import com.smtm.pickle.presentation.designsystem.components.dialog.model.PickleDialogButtonLayout
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import kotlinx.coroutines.launch

@Composable
fun MateInvitationDialog(
    modifier: Modifier = Modifier,
    invitationCode: String,
    onPrimaryClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val clipBoardManager = LocalClipboard.current
    val clipData = ClipData.newPlainText("invitationCode", invitationCode)

    PickleDialog(
        modifier = modifier,
        title = stringResource(R.string.invite_dialog_title),
        buttonLayout = PickleDialogButtonLayout.Vertical(
            primaryText = stringResource(R.string.invite_primary_click),
            ghostText = stringResource(R.string.invite_ghost_click),
            onPrimaryClick = onPrimaryClick,
            onGhostClick = onDismiss
        ),
        onDismiss = onDismiss,
        inputField = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.inputHeight),
                color = PickleTheme.colors.background50,
                shape = RoundedCornerShape(Dimensions.radius)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = invitationCode,
                        style = PickleTheme.typography.body2Medium,
                        color = PickleTheme.colors.gray800
                    )
                    PickleIconButtonWithTouchCustom(
                        iconRes = R.drawable.ic_common_copy,
                        onClick = {
                            scope.launch { clipBoardManager.setClipEntry(ClipEntry(clipData)) }
                            onDismiss()
                        }
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun MateInvitationDialogPreview() {
    PickleTheme {
        MateInvitationDialog(
            invitationCode = "ABCDEFG",
            onPrimaryClick = {},
            onDismiss = {}
        )
    }
}
