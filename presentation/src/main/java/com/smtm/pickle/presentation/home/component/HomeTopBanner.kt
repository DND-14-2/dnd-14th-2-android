package com.smtm.pickle.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun HomeTopBanner(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onCloseClick: () -> Unit,
) {

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .background(color = PickleTheme.colors.base0)
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .height(72.dp)
            .clickable(
                onClick = onClick
            ),
        color = PickleTheme.colors.gray50,
        shape = RoundedCornerShape(Dimensions.radiusSurface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = R.drawable.ic_home_banner_card),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "친구에게 소비심판 받기",
                    style = PickleTheme.typography.body1Bold,
                    color = PickleTheme.colors.gray700,
                )

                Text(
                    text = "내 초대코드를 전송해요",
                    style = PickleTheme.typography.body4Medium,
                    color = PickleTheme.colors.gray600,
                )
            }

            IconButton(
                modifier = Modifier.size(48.dp),
                onClick = onCloseClick,
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_home_banner_close),
                    contentDescription = "close",
                    tint = Color.Unspecified,
                )
            }
        }
    }
}

@Preview(
    name = "HomeTopBanner",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun HomeTopBannerPreview() {
    PickleTheme {
        HomeTopBanner(
            onClick = {},
            onCloseClick = {},
        )
    }
}