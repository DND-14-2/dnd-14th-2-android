package com.smtm.pickle.presentation.designsystem.components.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.profile.model.ProfileSizeType
import com.smtm.pickle.presentation.designsystem.components.profile.model.ProfileStatus
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun PickleProfile(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int = R.drawable.illust_profile_default,
    sizyType: ProfileSizeType = ProfileSizeType.NORMAL,
    selected: Boolean = false,
    isNewBadge: Boolean = false,
    enabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    val colors = PickleTheme.colors

    val clickableModifier = remember(onClick) {
        if (onClick != null) {
            Modifier.clickable(enabled = enabled, onClick = onClick)
        } else {
            Modifier
        }
    }
    val border = remember(selected) {
        if (selected) {
            BorderStroke(width = 2.dp, color = colors.primary400)
        } else {
            BorderStroke(width = 1.dp, color = colors.gray200)
        }
    }

    Box(
        modifier = Modifier.padding(top = 2.dp)
    ) {
        Surface(
            modifier = modifier
                .size(sizyType.size)
                .then(clickableModifier),
            shape = RoundedCornerShape(sizyType.cornerRadius),
            border = border,
            color = PickleTheme.colors.gray100,
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(sizyType.size),
            )
        }

        if (selected) {
            BadgeStateIcon(R.drawable.ic_common_check)
        }

        if (isNewBadge) {
            BadgeStateIcon(R.drawable.ic_common_new)
        }
    }
}

@Composable
private fun BoxScope.BadgeStateIcon(@DrawableRes badgeIconRes: Int) {
    Icon(
        painter = painterResource(id = badgeIconRes),
        contentDescription = null,
        modifier = Modifier
            .align(alignment = Alignment.TopEnd)
            .offset(4.5.dp, (-4.5).dp),
        tint = Color.Unspecified,
    )
}

@Composable
fun PickleCircleProfile(
    nickname: String,
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int = R.drawable.illust_profile_default,
    status: ProfileStatus = ProfileStatus.DEFAULT,
    selected: Boolean = false,
) {
    val colors = PickleTheme.colors

    val statusColor = remember(status) {
        if (status == ProfileStatus.COMPLETE) {
            colors.primary400
        } else {
            colors.gray400
        }
    }

    val (nicknameColor, border) = remember(status, selected) {
        if (selected && status == ProfileStatus.DEFAULT ||
            selected && status == ProfileStatus.COMPLETE
        ) {
            colors.gray700 to BorderStroke(width = 2.dp, color = colors.primary400)
        } else {
            colors.gray600 to null
        }
    }

    val offset = remember(status) {
        if (status != ProfileStatus.DEFAULT) 9.dp else 0.dp
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(Dimensions.profileSizeCircle)
        ) {
            Surface(
                modifier = Modifier.align(alignment = Alignment.TopCenter),
                border = border,
                shape = CircleShape
            ) {
                Image(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                )
            }

            if (status != ProfileStatus.DEFAULT) {
                Surface(
                    modifier = Modifier
                        .align(alignment = Alignment.BottomCenter)
                        .offset(y = offset),
                    shape = CircleShape,
                    color = statusColor
                ) {
                    Text(
                        text = getStatus(status),
                        style = PickleTheme.typography.caption1Medium,
                        color = PickleTheme.colors.base0,
                        modifier = Modifier.padding(vertical = 1.5.dp, horizontal = 7.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp + offset))
        Text(
            text = nickname,
            style = PickleTheme.typography.body4Medium,
            color = nicknameColor
        )
    }
}

private fun getStatus(status: ProfileStatus): String {
    return when (status) {
        ProfileStatus.DEFAULT -> ""
        ProfileStatus.WAITING -> "대기중"
        ProfileStatus.COMPLETE -> "완료"
    }
}

@Preview
@Composable
private fun PickleProfilePreview() {
    PickleTheme {
        Row {
            PickleProfile(sizyType = ProfileSizeType.InSetting)

            Spacer(modifier = Modifier.width(10.dp))

            PickleProfile(sizyType = ProfileSizeType.InSetting, selected = true)
        }
    }
}

@Preview
@Composable
private fun PickleCircleProfilePreview() {
    PickleTheme {
        Row {
            PickleCircleProfile(
                nickname = "닉네임",
                status = ProfileStatus.DEFAULT,
                selected = true
            )
            Spacer(modifier = Modifier.width(5.dp))
            PickleCircleProfile(
                nickname = "닉네임",
                status = ProfileStatus.DEFAULT,
            )
            Spacer(modifier = Modifier.width(5.dp))
            PickleCircleProfile(
                nickname = "닉네임",
                status = ProfileStatus.COMPLETE,
                selected = true
            )
            Spacer(modifier = Modifier.width(5.dp))
            PickleCircleProfile(
                nickname = "닉네임",
                status = ProfileStatus.WAITING,
            )
            Spacer(modifier = Modifier.width(5.dp))
            PickleCircleProfile(
                nickname = "닉네임",
                status = ProfileStatus.COMPLETE,
            )
        }
    }
}
