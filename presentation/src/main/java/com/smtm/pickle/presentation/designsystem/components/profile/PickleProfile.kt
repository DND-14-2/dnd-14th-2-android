package com.smtm.pickle.presentation.designsystem.components.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
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
import com.smtm.pickle.presentation.designsystem.components.profile.model.ProfileStatus
import com.smtm.pickle.presentation.designsystem.components.profile.model.ProfileType
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun PickleProfile(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int = R.drawable.illust_profile_default,
    type: ProfileType = ProfileType.NORMAL,
    enabled: Boolean = false,
    onClick: () -> Unit = {},
) {
    Surface(
        modifier = modifier.size(type.size),
        shape = RoundedCornerShape(type.cornerRadius),
        border = BorderStroke(width = 1.dp, color = PickleTheme.colors.gray200),
        color = PickleTheme.colors.gray100,
        enabled = enabled,
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

@Composable
fun PickleCircleProfile(
    nickname: String,
    modifier: Modifier = Modifier,
    status: ProfileStatus = ProfileStatus.DEFAULT,
    @DrawableRes iconRes: Int = R.drawable.illust_profile_default,
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
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    tint = Color.Unspecified
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
        PickleProfile(type = ProfileType.InSetting)
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
