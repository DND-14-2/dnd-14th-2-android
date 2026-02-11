package com.smtm.pickle.presentation.common.utils

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.snackbar.PickleSnackbar
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarDuration
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarPosition
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState

@Composable
fun BackPressFinishHandler(
    snackBarState: SnackbarState,
    backPressedMillis: Long = 2000L,
) {
    val context = LocalContext.current
    var backPressedTime by remember { mutableLongStateOf(0L) }
    val message = stringResource(R.string.back_pressed_message)

    BackHandler {
        val currentTime = System.currentTimeMillis()

        if (currentTime - backPressedTime < backPressedMillis) {
            (context as? Activity)?.finish()
        } else {
            backPressedTime = currentTime
            snackBarState.show(
                PickleSnackbar.custom(
                    message = message,
                    duration = SnackbarDuration.TOAST_SHORT.duration,
                    position = SnackbarPosition.AboveBottomContents
                )
            )
        }
    }
}
