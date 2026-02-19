package com.smtm.pickle.presentation.verdict.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import com.smtm.pickle.presentation.mypage.tabs.statistics.components.SlidingPillIndicator
import com.smtm.pickle.presentation.verdict.model.VerdictCounts

@Composable
fun VerdictTabs(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    myJudgementFilterIndex: Int,
    myVerdictFilterIndex: Int,
    myJudgementCounts: VerdictCounts,
    myVerdictCounts: VerdictCounts,
    onTabSelected: (Int) -> Unit,
    onFilterSelected: (Int) -> Unit,
) {
    val tabs = listOf("내 심판", "내 판결")

    val filters = if (selectedTabIndex == 0) {
        listOf(
            "전체 ${myJudgementCounts.total}",
            "대기 ${myJudgementCounts.pending}",
            "완료 ${myJudgementCounts.completed}"
        )
    } else {
        listOf(
            "전체 ${myVerdictCounts.total}",
            "보류 ${myVerdictCounts.pending}",
            "완료 ${myVerdictCounts.completed}"
        )
    }

    val currentFilterIndex = if (selectedTabIndex == 0) myJudgementFilterIndex else myVerdictFilterIndex

    Column(modifier = modifier) {
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
                                color = PickleTheme.colors.gray800
                            )
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            filters.forEachIndexed { index, text ->
                VerdictChip(
                    text = text,
                    onClick = { onFilterSelected(index) },
                    selected = index == currentFilterIndex,
                )
            }
        }
    }
}

@Composable
private fun VerdictChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = PickleTheme.colors

    Box(
        modifier = modifier
            .height(36.dp)
            .clip(RoundedCornerShape(Dimensions.radiusFull))
            .background(if (selected) colors.gray700 else colors.background50)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = PickleTheme.typography.body4Medium,
            color = remember(selected) { if (selected) colors.base0 else colors.gray700 },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Preview
@Composable
private fun VerdictTabsPreview() {
    PickleTheme {
        VerdictTabs(
            selectedTabIndex = 0,
            myJudgementFilterIndex = 0,
            myVerdictFilterIndex = 0,
            myJudgementCounts = VerdictCounts(10, 5, 5),
            myVerdictCounts = VerdictCounts(5, 3, 2),
            onTabSelected = {},
            onFilterSelected = {}
        )
    }
}
