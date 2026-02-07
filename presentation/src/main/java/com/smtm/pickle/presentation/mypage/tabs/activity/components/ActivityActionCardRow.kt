package com.smtm.pickle.presentation.mypage.tabs.activity.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.PickleCard
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun ActivityActionCardRow(
    modifier: Modifier = Modifier,
    onJudgmentClick: () -> Unit,
    onJuryClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ActivityActionCard(
            title = "심판 활동",
            subTitle = "내가 심판받은 소비",
            iconRes = R.drawable.illust_mypage_balances,
            onClick = onJudgmentClick
        )
        ActivityActionCard(
            title = "배심 활동",
            subTitle = "내가 판결한 소비",
            iconRes = R.drawable.illust_mypage_gavel,
            onClick = onJuryClick
        )
    }
}

@Composable
private fun RowScope.ActivityActionCard(
    title: String,
    subTitle: String,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit
) {
    PickleCard(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(Dimensions.radiusSurface))
            .clickable(onClick = onClick),
        contentPadding = PaddingValues(16.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    style = PickleTheme.typography.body1Bold,
                    color = PickleTheme.colors.gray700
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subTitle,
                    style = PickleTheme.typography.caption1Medium,
                    color = PickleTheme.colors.gray600
                )
            }
            Icon(
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .fillMaxSize(0.7f)
                    .offset(6.dp, 6.dp),
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}

@Preview
@Composable
private fun ActivityActionCardRowPreview() {
    PickleTheme {
        ActivityActionCardRow(
            onJudgmentClick = {},
            onJuryClick = {}
        )
    }
}
