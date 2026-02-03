package com.smtm.pickle.presentation.mypage.tabs.statistics.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.zIndex
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import kotlin.math.abs

@SuppressLint("FrequentlyChangingValue")
@Composable
fun SlidingPillIndicator(
    positions: List<TabPosition>,
    selectedIndex: Int,
) {
    val animatedIndex by animateFloatAsState(
        targetValue = selectedIndex.toFloat(),
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "IndicatorAnimation"
    )

    val curr = animatedIndex.toInt()
    val off = animatedIndex - curr
    val next = (curr + 1).coerceAtMost(positions.lastIndex)

    val start = positions.getOrNull(curr) ?: return
    val end = positions.getOrNull(next) ?: start
    val fraction = abs(off)

    val tabLeft = lerp(start.left, end.left, fraction)
    val tabWidth = lerp(start.width, end.width, fraction)
    val pillWidth = tabWidth * 1f
    val pillLeft = tabLeft + (tabWidth - pillWidth) / 2

    Box(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .align(Alignment.CenterStart)
                .offset(x = pillLeft)
                .width(pillWidth)
                .height(36.dp)
                .clip(RoundedCornerShape(Dimensions.radiusSmall))
                .background(PickleTheme.colors.base0)
                .zIndex(-1f)
        )
    }
}

@Preview
@Composable
private fun SlidingPillIndicatorPreview() {
    PickleTheme {
        SlidingPillIndicator(
            positions = listOf(),
            selectedIndex = 0,
        )
    }
}
