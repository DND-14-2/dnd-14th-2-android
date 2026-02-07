package com.smtm.pickle.presentation.designsystem.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.model.ChipShape
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

/**
 * 칩
 * @param text 텍스트
 * @param modifier
 * @param selected 칩 선택 여부
 * @param height 칩 높이
 * @param border 칩 테두리
 * @param shape 칩 모양
 * @param containerColor 컨테이너 색상
 * @param textColor 텍스트 색상
 * @param contentPadding 컨텐츠 패딩
 * @param leadingIcon 왼쪽 아이콘
 * @param trailingIcon 오른쪽 아이콘
 * @param onClick 클릭 이벤트
 */
@Composable
fun PickleChip(
    text: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    height: Dp = Dimensions.chipHeight,
    border: BorderStroke? = null,
    shape: ChipShape = ChipShape.Capsule,
    containerColor: Color = PickleTheme.colors.gray100,
    textColor: Color = PickleTheme.colors.gray700,
    contentPadding: PaddingValues = PaddingValues(8.dp),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    val colors = PickleTheme.colors

    val iconSpace = remember(leadingIcon, trailingIcon) {
        if (leadingIcon != null && trailingIcon != null) 8.dp else 4.dp
    }

    Surface(
        modifier = modifier
            .height(height)
            .clip(getChipShape(shape))
            .then(
                if (onClick == null) Modifier else Modifier.clickable(onClick = onClick)
            ),
        color = remember(selected) {
            if (selected) colors.gray700 else containerColor
        },
        shape = getChipShape(shape),
        border = border
    ) {
        Row(
            modifier = Modifier.padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                leadingIcon()
                Spacer(modifier = Modifier.width(iconSpace))
            }
            Text(
                text = text,
                style = PickleTheme.typography.body4Medium,
                color = remember(selected) {
                    if (selected) colors.base0 else textColor
                }
            )
            trailingIcon?.let {
                Spacer(modifier = Modifier.width(iconSpace))
                trailingIcon()
            }
        }
    }
}

@Composable
fun PickleBadge(
    text: String
) {
    PickleChip(
        text = text,
        shape = ChipShape.Badge,
        height = Dimensions.chipHeightBadge,
        containerColor = PickleTheme.colors.base0,
        textColor = PickleTheme.colors.gray700,
        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 3.5.dp),
        border = BorderStroke(width = 1.dp, color = PickleTheme.colors.gray200)
    )
}

@Composable
private fun getChipShape(shape: ChipShape): Shape {
    return when (shape) {
        ChipShape.Capsule -> RoundedCornerShape(Dimensions.radiusFull)
        ChipShape.Square -> RoundedCornerShape(8.dp)
        ChipShape.Badge -> RoundedCornerShape(6.dp)
    }
}

@Preview
@Composable
private fun PickleChipPreview() {
    PickleTheme {
        PickleChip(
            text = "초대하기",
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search_close),
                    contentDescription = null,
                    modifier = Modifier.size(Dimensions.iconSmall)
                )
            },
            contentPadding = PaddingValues(start = 10.dp, end = 6.dp, top = 6.dp, bottom = 6.dp)
        )
    }
}

@Preview
@Composable
private fun PickleBadgePreview() {
    PickleTheme {
        PickleBadge(text = "배지명")
    }
}
