package com.smtm.pickle.presentation.designsystem.components.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.common.extension.customLayout

/**
 * 커스텀 상호작용 아이콘 버튼
 * @param iconRes 아이콘 리소스
 * @param onClick 클릭 이벤트
 * @param modifier
 * @param iconSize 눈에 보이는 아이콘 크기
 * @param touchSize 터치 가능한 영역 (Material 최소 터치 영역 48 권장)
 * @param layoutSize 부모 레이아웃이 인식하는 크기
 * @param contentDescription 아이콘 설명
 * @param tint 아이콘 색상
 */
@Composable
fun PickleIconButtonWithTouchCustom(
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
    touchSize: Dp = 48.dp,
    layoutSize: Dp = 24.dp,
    contentDescription: String? = null,
    tint: Color = Color.Unspecified,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .customLayout(
                contentSize = touchSize,
                layoutSize = layoutSize
            ) // 클릭 이벤트 및 리플 효과 설정
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(
                    bounded = false,
                    radius = maxOf(touchSize / 2, 24.dp)
                ),
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            painter = painterResource(iconRes),
            contentDescription = contentDescription,
            tint = tint
        )
    }
}
