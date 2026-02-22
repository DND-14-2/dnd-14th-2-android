package com.smtm.pickle.presentation.login.nickname.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        title = "함께 소비를 개선할\n친구를 초대할까요?",
        buttonLayout = PickleDialogButtonLayout.Vertical(
            primaryText = "초대하기",
            ghostText = "괜찮아요",
            onPrimaryClick = onPrimaryButtonClick,
            onGhostClick = onGhostButtonClick,
            action = PickleDialogButtonLayout.Action(
                text = "이미 초대코드를 받았어요",
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