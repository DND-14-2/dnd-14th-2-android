package com.smtm.pickle.presentation.mypage.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.PickleCard
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun ProfileMyBadgeCard(
    modifier: Modifier = Modifier,
    onMyBadgeClick: () -> Unit,
) {
    PickleCard(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(Dimensions.radius))
            .clickable(onClick = onMyBadgeClick),
        color = PickleTheme.colors.primary50
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_mypage_badge),
                contentDescription = null
            )
            Text(
                text = "내 배지 보기",
                style = PickleTheme.typography.body2Medium,
                color = PickleTheme.colors.gray800,
                modifier = Modifier.padding(start = 6.dp)
            )
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(R.drawable.ic_mypage_arrow_right),
                contentDescription = null,
                tint = PickleTheme.colors.gray500
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileMyBadgeCardPreview() {
    PickleTheme {
        ProfileMyBadgeCard {}
    }
}
