package com.smtm.pickle.presentation.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.components.PicklePageIndicator
import com.smtm.pickle.presentation.onboarding.model.OnboardingItems

@Composable
fun OnboardingPagerSection(
    modifier: Modifier = Modifier,
    pagerState: PagerState
) {
    val onboardingSteps = listOf(
        OnboardingItems.Step1,
        OnboardingItems.Step2,
        OnboardingItems.Step3
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
        ) { page ->
            OnboardingPagerContent(
                content = onboardingSteps[page]
            )
        }
        Spacer(Modifier.height(20.dp))

        PicklePageIndicator(
            currentPage = pagerState.currentPage,
            pageCount = pagerState.pageCount,
        )
    }
}
