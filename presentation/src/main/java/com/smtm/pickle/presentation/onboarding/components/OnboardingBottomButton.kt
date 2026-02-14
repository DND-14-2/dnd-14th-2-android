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
import com.smtm.pickle.presentation.designsystem.components.button.PickleButtonGroup
import com.smtm.pickle.presentation.designsystem.components.button.PickleButtonV2
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonGroupLayout
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonSize
import com.smtm.pickle.presentation.designsystem.components.button.model.PickleButtonType

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
            PickleButtonGroup(
                modifier = Modifier.fillMaxWidth(),
                layout = PickleButtonGroupLayout.RowFixedLeading,
                buttonSize = PickleButtonSize.Large,
                leadingButton = { modifier, buttonSize ->
                    PickleButtonV2(
                        modifier = modifier,
                        text = stringResource(R.string.common_previous),
                        onClick = onPrev,
                        type = PickleButtonType.Secondary,
                        size = buttonSize,
                    )
                },
                trailingButton = { modifier, buttonSize ->
                    PickleButtonV2(
                        modifier = modifier,
                        text = if (isLast) stringResource(R.string.onboarding_start) else stringResource(R.string.common_next),
                        onClick = { if (isLast) onFinish() else onNext() },
                        size = buttonSize,
                    )
                }
            )
        } else {
            PickleButtonV2(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.onboarding_skip),
                onClick = onFinish,
            )
        }
    }
}
