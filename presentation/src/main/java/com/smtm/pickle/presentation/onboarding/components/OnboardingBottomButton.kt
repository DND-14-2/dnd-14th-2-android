package com.smtm.pickle.presentation.onboarding.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.PickleButton
import com.smtm.pickle.presentation.designsystem.components.button.PickleButtonWithCancel
import com.smtm.pickle.presentation.designsystem.components.button.model.CancelWidth

@Composable
fun OnboardingBottomButton(
    currentPage: Int,
    lastPageIndex: Int,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onFinish: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 14.dp, top = 6.dp)
            .navigationBarsPadding(),
    ) {
        val isLast = currentPage == lastPageIndex

        if (currentPage > 0) {
            PickleButtonWithCancel(
                confirmText = if (isLast) stringResource(R.string.onboarding_start) else stringResource(R.string.common_next),
                cancelText = stringResource(R.string.common_previous),
                onConfirmClick = { if (isLast) onFinish() else onNext() },
                onCancelClick = onPrev,
                cancelWidth = CancelWidth.Medium
            )
        } else {
            PickleButton(
                text = stringResource(R.string.common_next),
                onClick = onNext,
            )
        }
    }
}
