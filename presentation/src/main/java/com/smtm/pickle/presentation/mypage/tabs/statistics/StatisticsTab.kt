package com.smtm.pickle.presentation.mypage.tabs.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import com.smtm.pickle.presentation.mypage.MyPageUiState
import com.smtm.pickle.presentation.mypage.tabs.statistics.components.SlidingPillIndicator
import com.smtm.pickle.presentation.mypage.tabs.statistics.components.StatsChartCard
import com.smtm.pickle.presentation.mypage.tabs.statistics.components.StatsEmptyChart
import com.smtm.pickle.presentation.mypage.tabs.statistics.components.StatsSummaryCard
import com.smtm.pickle.presentation.mypage.tabs.statistics.model.DonutChartItem

@Composable
fun StatisticsTab(
    modifier: Modifier = Modifier,
    statisticsState: MyPageUiState.StatisticsState,
    onMyLedgerClick: () -> Unit,
    onTabSelected: (Int) -> Unit,
) {
    val tabs = listOf("지출", "수입")
    val selectedTabIndex = statisticsState.selectedTabIndex

    val currentDetail = if (selectedTabIndex == 0) {
        statisticsState.expenditure
    } else {
        statisticsState.income
    }

    val chartItems = currentDetail.chartItems.map { item ->
        DonutChartItem(
            label = item.label,
            value = item.value,
            color = Color(item.colorHex)
        )
    }.sortedByDescending { it.value }

    Column(
        modifier = modifier
            .background(PickleTheme.colors.background50)
            .padding(16.dp)
            .padding(top = 4.dp)
    ) {
        // 하위 TabRow의 InteractionSource 삭제
        CompositionLocalProvider(LocalRippleConfiguration provides null) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = PickleTheme.colors.background100,
                contentColor = PickleTheme.colors.gray800,
                indicator = { tabPosition ->
                    SlidingPillIndicator(
                        positions = tabPosition,
                        selectedIndex = selectedTabIndex,
                    )
                },
                divider = { },
                modifier = Modifier
                    .height(40.dp)
                    .clip(RoundedCornerShape(Dimensions.radius))
                    .background(PickleTheme.colors.background100)
                    .padding(2.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = index == selectedTabIndex,
                        modifier = Modifier
                            .zIndex(1f)
                            .clip(RoundedCornerShape(Dimensions.radiusSmall)),
                        onClick = { onTabSelected(index) },
                        text = {
                            Text(
                                text = title,
                                style = PickleTheme.typography.body4Medium,
                            )
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        StatisticsContents(
            isExpenditure = selectedTabIndex == 0,
            chartItems = chartItems,
            cost = currentDetail.totalAmount,
            comparedValue = currentDetail.comparedToPreviousMonth,
            onMyLedgerClick = onMyLedgerClick,
            month = currentDetail.month
        )
    }
}

@Composable
private fun StatisticsContents(
    isExpenditure: Boolean,
    chartItems: List<DonutChartItem> = emptyList(),
    cost: Long,
    comparedValue: Long,
    onMyLedgerClick: () -> Unit,
    month: Int,
) {
    StatsSummaryCard(
        isExpenditure = isExpenditure,
        cost = cost,
        onMyLedgerClick = onMyLedgerClick,
        comparedValue = comparedValue
    )
    Spacer(modifier = Modifier.height(20.dp))

    if (chartItems.isEmpty()) {
        StatsEmptyChart()
    } else {
        StatsChartCard(
            isExpenditure = isExpenditure,
            chartItems = chartItems,
            month = month
        )
    }
}


@Preview
@Composable
private fun StatisticsTabPreview() {
    PickleTheme {
        StatisticsTab(
            statisticsState = MyPageUiState.StatisticsState(),
            onMyLedgerClick = {},
            onTabSelected = {}
        )
    }
}
