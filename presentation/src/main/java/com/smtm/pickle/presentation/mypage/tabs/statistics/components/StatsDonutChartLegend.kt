package com.smtm.pickle.presentation.mypage.tabs.statistics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.mypage.tabs.statistics.model.DonutChartItem

@Composable
fun DonutChartLegend(
    items: List<DonutChartItem>,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        itemVerticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEach { item ->
            LegendItem(item)
        }
    }
}

@Composable
private fun LegendItem(item: DonutChartItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.wrapContentWidth()
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(item.color)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = item.label,
            style = PickleTheme.typography.caption1Medium,
            color = PickleTheme.colors.gray700
        )
    }
}

@Preview
@Composable
private fun DonutChartLegendPreview() {
    val chartItems = listOf(
        DonutChartItem("식비", 30f, Color(0xFF2BC4C1)),
        DonutChartItem("교통비", 15f, Color(0xFFFFDD52)),
        DonutChartItem("주거비", 10f, Color(0xFF4493FF)),
        DonutChartItem("쇼핑", 20f, Color(0xFFFF70A7)),
        DonutChartItem("의료/건강", 8f, Color(0xFF63C3FF)),
        DonutChartItem("교육/자기계발", 4f, Color(0xFF75C375)),
        DonutChartItem("여가/취미", 12f, Color(0xFFB362FF)),
        DonutChartItem("저축/금융", 28f, Color(0xFFFF9429)),
        DonutChartItem("기타", 5f, Color(0xFFAAAAAA)),
    ).sortedBy { it.value }

    PickleTheme {
        DonutChartLegend(items = chartItems)
    }
}
