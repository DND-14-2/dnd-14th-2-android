package com.smtm.pickle.presentation.ledger.detail.component

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class LedgerDetailReceiptShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val cornerRadius = with(density) { 12.dp.toPx() }
        val zigzagWidth = 20f
        val zigzagHeight = 20f

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

            // 우측 세로선 (톱니 시작점까지)
            lineTo(size.width, size.height - zigzagHeight)

            // 하단 톱니 모양 (우측에서 좌측으로)
            var currentX = size.width
            while (currentX > 0) {
                lineTo(currentX - zigzagWidth / 2, size.height)
                lineTo(currentX - zigzagWidth, size.height - zigzagHeight)
                currentX -= zigzagWidth
            }

            // 좌측 세로선으로 복귀
            lineTo(0f, size.height - zigzagHeight)
            lineTo(0f, cornerRadius)

            close()
        }
        return Outline.Generic(path)
    }
}