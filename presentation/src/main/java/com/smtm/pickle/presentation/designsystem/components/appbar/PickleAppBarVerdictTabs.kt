package com.smtm.pickle.presentation.designsystem.components.appbar

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabIndicatorScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.zIndex
import com.smtm.pickle.presentation.designsystem.components.appbar.model.VerdictTab
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import kotlin.math.abs

@Composable
fun PickleAppBarVerdictTabs(
    selectedTab: VerdictTab,
    onTabSelect: (VerdictTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs = VerdictTab.entries
    val selectedIndex = tabs.indexOf(selectedTab)

    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        SecondaryTabRow(
            selectedTabIndex = selectedIndex,
            modifier = modifier
                .height(40.dp)
                .clip(RoundedCornerShape(Dimensions.radius))
                .background(PickleTheme.colors.background100)
                .padding(2.dp),
            containerColor = PickleTheme.colors.background100,
            contentColor = PickleTheme.colors.gray800,
            indicator = {
                SlidingPillIndicator(selectedIndex = selectedIndex)
            },
            divider = {},
        ) {
            tabs.forEach { tab ->
                Tab(
                    selected = selectedTab == tab,
                    onClick = { onTabSelect(tab) },
                    modifier = Modifier
                        .clip(RoundedCornerShape(Dimensions.radiusSmall))
                        .zIndex(1f),
                    text = {
                        Text(
                            text = tab.displayName,
                            style = PickleTheme.typography.body4Medium,
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun TabIndicatorScope.SlidingPillIndicator(selectedIndex: Int) {
    val animatedIndexState = animateFloatAsState(
        targetValue = selectedIndex.toFloat(),
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "IndicatorAnimation"
    )

    Box(
        Modifier
            .tabIndicatorLayout { measurable, constraints, tabPositions ->
                if (tabPositions.isEmpty()) return@tabIndicatorLayout layout(0, 0) {}

                val animatedIndex = animatedIndexState.value
                val curr = animatedIndex.toInt()
                val off = animatedIndex - curr
                val next = (curr + 1).coerceAtMost(tabPositions.lastIndex)

                val start = tabPositions[curr]
                val end = tabPositions[next]
                val fraction = abs(off)

                val tabLeft = lerp(start.left, end.left, fraction)
                val tabWidth = lerp(start.width, end.width, fraction)

                val pillWidthPx = tabWidth.roundToPx()
                val pillHeightPx = 36.dp.roundToPx()
                val pillLeftPx = tabLeft.roundToPx()

                val placeable = measurable.measure(Constraints.fixed(pillWidthPx, pillHeightPx))

                layout(constraints.maxWidth, constraints.maxHeight) {
                    val verticalOffset = (constraints.maxHeight - pillHeightPx) / 2
                    placeable.placeRelative(pillLeftPx, verticalOffset)
                }
            }
            .clip(RoundedCornerShape(Dimensions.radiusSmall))
            .background(PickleTheme.colors.base0)
    )
}

@Preview
@Composable
private fun PickleAppBarVerdictTabsPreview() {
    PickleTheme {
        var selected by remember { mutableStateOf(VerdictTab.Trial) }
        PickleAppBarVerdictTabs(
            selectedTab = selected,
            onTabSelect = { selected = it },
        )
    }
}
