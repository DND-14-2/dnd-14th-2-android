package com.smtm.pickle.presentation.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.common.extension.customLayout

/**
 * 커스텀 아이콘
 * @param iconRes 아이콘 리소스
 * @param modifier
 * @param iconSize 눈에 보이는 아이콘 크기
 * @param layoutSize 부모 레이아웃이 인식하는 크기
 * @param contentDescription 아이콘 설명
 * @param tint 아이콘 색상
 */
@Composable
fun PickleIcon(
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
    layoutSize: Dp = 24.dp,
    contentDescription: String? = null,
    tint: Color = Color.Unspecified
) {
    Box(
        modifier = modifier.customLayout(
            contentSize = iconSize,
            layoutSize = layoutSize
        ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.size(iconSize),
            tint = tint
        )
    }
}
