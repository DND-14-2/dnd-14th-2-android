package com.smtm.pickle.presentation.mypage.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.mypage.MyPageUiState
import com.smtm.pickle.presentation.mypage.tabs.activity.ActivityTab
import com.smtm.pickle.presentation.mypage.tabs.statistics.StatisticsTab
import kotlinx.coroutines.launch

@Composable
fun MyPageTabSection(
    modifier: Modifier = Modifier,
    statisticsState: MyPageUiState.StatisticsState,
    activityState: MyPageUiState.ActivityState,
    onNavigateMyLedger: () -> Unit,
    onStatisticsTabSelected: (Int) -> Unit,
) {
    val tabs = listOf("통계", "활동")
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()

    Column(modifier = modifier) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = PickleTheme.colors.base0,
            contentColor = PickleTheme.colors.gray800,
            indicator = { tabPosition ->
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPosition[pagerState.currentPage]),
                    width = 164.dp,
                    color = PickleTheme.colors.gray800,
                    height = 2.dp,
                    shape = RectangleShape
                )
            },
            divider = {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = PickleTheme.colors.gray200
                )
            },
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = index == pagerState.currentPage,
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(index) }
                    },
                    text = {
                        Text(
                            text = title,
                            style = PickleTheme.typography.body2Medium,
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { page ->
            when (page) {
                0 -> StatisticsTab(
                    statisticsState = statisticsState,
                    onMyLedgerClick = onNavigateMyLedger,
                    onTabSelected = onStatisticsTabSelected,
                )

                1 -> ActivityTab(
                    activityState = activityState,
                )
            }
        }
    }
}
