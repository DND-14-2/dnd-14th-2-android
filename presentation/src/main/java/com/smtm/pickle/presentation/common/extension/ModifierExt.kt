package com.smtm.pickle.presentation.common.extension

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import kotlin.math.hypot

/**
 * 카드 섀도우
 *
 * @sample com.smtm.pickle.presentation.designsystem.components.PickleCardPreview
 */
@Composable
fun Modifier.pickleShadow(
    shadowColor: Color = PickleTheme.colors.primary600,
    elevation: Dp = 50.dp // TODO: 사이즈 고려
) = graphicsLayer {

    this.shadowElevation = elevation.toPx()
    this.shape = RoundedCornerShape(Dimensions.radiusSurface)
    this.clip = false
    this.ambientShadowColor = shadowColor
    this.spotShadowColor = shadowColor
}

@Stable
fun Modifier.clearFocusOnBackgroundTab(
    focusManager: FocusManager
) = pointerInput(focusManager) {
    awaitEachGesture {
        val down = awaitFirstDown(
            pass = PointerEventPass.Final, // 내부 Composable의 처리를 우선하기 위해 `PointerEventPass.Final` 사용
            requireUnconsumed = false
        )
        if (down.isConsumed) return@awaitEachGesture

        val pointerId = down.id
        val startPosition = down.position
        val touchSlop = viewConfiguration.touchSlop

        while (true) {
            val event = awaitPointerEvent(pass = PointerEventPass.Final)
            val eventChange = event.changes.firstOrNull { it.id == pointerId } ?: continue

            // UP인 경우
            if (!eventChange.pressed) {
                // Up이 소비되지 않은 경우
                if (!eventChange.isConsumed) {
                    focusManager.clearFocus(true)
                }
                return@awaitEachGesture
            }

            // 드래그 판별
            if (eventChange.positionChanged()) {
                // 스크롤 컨테이너가 MOVE 소비하면 드래그로 판별
                if (eventChange.isConsumed) return@awaitEachGesture

                // 드래그로 판별
                val delta = eventChange.position - startPosition
                val distance = hypot(delta.x, delta.y)
                if (distance > touchSlop) return@awaitEachGesture
            }
        }
    }
}