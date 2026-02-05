package com.smtm.pickle.presentation.mypage.tabs.statistics.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.smtm.pickle.presentation.designsystem.components.tooltip.PickleTooltip
import com.smtm.pickle.presentation.designsystem.components.tooltip.model.TailPosition
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.mypage.tabs.statistics.model.DonutChartItem
import kotlinx.coroutines.delay
import kotlin.collections.forEachIndexed
import kotlin.collections.map
import kotlin.math.atan2
import kotlin.math.roundToInt
import kotlin.math.sqrt

@Composable
fun DonutChart(
    items: List<DonutChartItem>,
    modifier: Modifier = Modifier,
    chartSize: Dp = 180.dp,
    holeSize: Dp = 72.dp,
) {
    // 전체 합계 계산
    val total = remember(items) { items.sumOf { it.value.toDouble() }.toFloat() }

    if (total <= 0f) return

    // 각 섹션의 각도 계산
    val angles = remember(items, total) {
        items.map { (it.value / total) * 360f }
    }

    // 클릭된 아이템 상태
    var selectedItem by remember { mutableStateOf<DonutChartItem?>(null) }
    var clickOffset by remember { mutableStateOf(Offset.Zero) }


    Box(
        modifier = modifier.size(chartSize)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(items) {
                    detectTapGestures { offset ->
                        // 클릭 위치가 어느 섹션인지 계산
                        val center = Offset(size.width / 2f, size.height / 2f)
                        val dx = offset.x - center.x
                        val dy = offset.y - center.y
                        val distance = sqrt(dx * dx + dy * dy)

                        // 도넛 영역 내부인지 확인
                        val outerRadius = size.width / 2f
                        val innerRadius = holeSize.toPx() / 2f

                        if (distance in innerRadius..outerRadius) {
                            // 클릭한 각도 계산
                            var angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()
                            angle = (angle + 180f) % 360f

                            // 어느 섹션인지 찾기
                            var currentAngle = 0f
                            items.forEachIndexed { index, item ->
                                val endAngle = currentAngle + angles[index]
                                if (angle in currentAngle..<endAngle) {
                                    selectedItem = item
                                    clickOffset = offset
                                    return@detectTapGestures
                                }
                                currentAngle = endAngle
                            }
                        }
                    }
                }
        ) {
            // 전체 크기를 chartSize로 맞춤
            val outerRadius = size.minDimension / 2f
            val innerRadius = holeSize.toPx() / 2f
            val strokeWidth = outerRadius - innerRadius

            // 중심점 계산
            val center = Offset(size.width / 2f, size.height / 2f)

            // Arc를 그릴 실제 크기
            val arcRadius = innerRadius + strokeWidth / 2f
            val arcSize = arcRadius * 2f

            // 왼쪽부터 시작 (180도)
            var startAngle = 180f

            items.forEachIndexed { index, item ->
                val sweepAngle = angles[index]

                drawArc(
                    color = item.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(
                        center.x - arcRadius,
                        center.y - arcRadius
                    ),
                    size = Size(arcSize, arcSize),
                    style = Stroke(width = strokeWidth)
                )

                startAngle += sweepAngle
            }
        }

        // 선택된 아이템의 팝업 표시
        selectedItem?.let { item ->
            val percentage = (item.value / total * 100).roundToInt()

            // 팝업을 클릭한 위치에 직접 표시
            Box(modifier = Modifier.offset { IntOffset(clickOffset.x.toInt(), clickOffset.y.toInt()) }) {
                Popup(
                    alignment = Alignment.Center,
                    offset = IntOffset(0, -80),
                    onDismissRequest = { selectedItem = null },
                    properties = PopupProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true,
                        focusable = true
                    )
                ) {
                    PickleTooltip(
                        message = "$percentage%",
                        subText = item.label,
                        tailPosition = TailPosition.BOTTOM,
                        isVisible = true,
                    )
                }
            }

            // 팝업 자동 닫기
            LaunchedEffect(selectedItem) {
                delay(2000)
                selectedItem = null
            }
        }
    }
}

@Preview
@Composable
private fun DonutChartPreview() {
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
        DonutChart(items = chartItems,)
    }
}
