package com.smtm.pickle.presentation.login.nickname.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.dialog.PickleDialog
import com.smtm.pickle.presentation.designsystem.components.dialog.model.PickleDialogButtonLayout

@Composable
fun WelcomeDialog(
    modifier: Modifier = Modifier,
    onStartClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    PickleDialog(
        title = stringResource(R.string.welcome_dialog_title),
        buttonLayout = PickleDialogButtonLayout.Single(
            text = stringResource(R.string.common_start),
            onClick = onStartClick
        ),
        onDismiss = onDismiss,
        modifier = modifier,
        imageRes = R.drawable.img_complete_signup,
    )
}