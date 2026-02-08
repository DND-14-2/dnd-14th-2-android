package com.smtm.pickle.presentation.ledger.detail.component

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

class LedgerDetailReceiptShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val cornerRadius = with(density) { 12.dp.toPx() }
        val desiredZigzagWidth = 40f

        // 전체 너비에 맞춰서 톱니 개수 계산
        val zigzagCount = (size.width / desiredZigzagWidth).roundToInt().coerceAtLeast(1)
        val actualZigzagWidth = size.width / zigzagCount
        val zigzagHeight = 30f

        val path = Path().apply {
            // 좌측 상단 라운드 코너에서 시작
            moveTo(0f, cornerRadius)

            // 좌측 상단 라운드
            arcTo(
                rect = Rect(0f, 0f, cornerRadius * 2, cornerRadius * 2),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            // 상단 가로선
            lineTo(size.width - cornerRadius, 0f)

            // 우측 상단 라운드
            arcTo(
                rect = Rect(size.width - cornerRadius * 2, 0f, size.width, cornerRadius * 2),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            // 우측 세로선 (톱니 시작 위치까지)
            lineTo(size.width, size.height)

            // 하단 톱니 모양 (우측에서 좌측으로) - 순수 이등변 삼각형
            for (i in 0 until zigzagCount) {
                val leftX = size.width - ((i + 1) * actualZigzagWidth)
                val midX = size.width - ((i + 0.5f) * actualZigzagWidth)

                // 위에서 아래 꼭지점으로 (좌측 변)
                lineTo(midX, size.height + zigzagHeight)

                // 꼭지점에서 다시 위로 (우측 변)
                lineTo(leftX, size.height)
            }

            // 좌측 세로선으로 복귀
            lineTo(0f, cornerRadius)

            close()
        }
        return Outline.Generic(path)
    }
}