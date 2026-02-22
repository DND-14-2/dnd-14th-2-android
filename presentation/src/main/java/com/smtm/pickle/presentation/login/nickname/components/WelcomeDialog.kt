package com.smtm.pickle.presentation.login.nickname.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        title = "피클에 오신 것을 환영해요",
        buttonLayout = PickleDialogButtonLayout.Single(
            text = "시작하기",
            onClick = onStartClick
        ),
        onDismiss = onDismiss,
        modifier = modifier,
        imageRes = R.drawable.img_pickle_code,
    )
}