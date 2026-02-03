package com.smtm.pickle.presentation.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.components.PicklePageIndicator

@Composable
fun OnboardingPagerSection(
    modifier: Modifier = Modifier,
    pagerState: PagerState
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
        ) {
            // TODO: 컨텐츠 변경
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(horizontal = 16.dp)
                    .background(Color(0xFFF5F5F5))
            )
        }

        Spacer(Modifier.height(29.dp))

        PicklePageIndicator(
            currentPage = pagerState.currentPage,
            pageCount = pagerState.pageCount,
        )
    }
}
