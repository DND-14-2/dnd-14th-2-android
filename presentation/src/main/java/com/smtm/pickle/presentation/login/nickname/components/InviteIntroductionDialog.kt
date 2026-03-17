package com.smtm.pickle.presentation.login.nickname.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.dialog.PickleDialog
import com.smtm.pickle.presentation.designsystem.components.dialog.model.PickleDialogButtonLayout
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun InviteIntroductionDialog(
    modifier: Modifier = Modifier,
    onPrimaryButtonClick: () -> Unit,
    onGhostButtonClick: () -> Unit,
    onAlreadyReceivedTextClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    PickleDialog(
        title = stringResource(R.string.invite_introduction_title),
        buttonLayout = PickleDialogButtonLayout.Vertical(
            primaryText = stringResource(R.string.invite_introduction_action),
            ghostText = stringResource(R.string.invite_introduction_ghost),
            onPrimaryClick = onPrimaryButtonClick,
            onGhostClick = onGhostButtonClick,
            action = PickleDialogButtonLayout.Action(
                text = stringResource(R.string.invite_introduction_already),
                onClick = onAlreadyReceivedTextClick
            )
        ),
        onDismiss = onDismiss,
        modifier = modifier,
        imageRes = R.drawable.img_pickle_code,
    )
}

                @Preview(
    name = "InviteIntroductionDialog",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun InviteIntroductionDialogPreview() {
    PickleTheme {
        InviteIntroductionDialog(
            onPrimaryButtonClick = {},
            onGhostButtonClick = {},
            onAlreadyReceivedTextClick = {},
            onDismiss = {}
        )
    }
}
