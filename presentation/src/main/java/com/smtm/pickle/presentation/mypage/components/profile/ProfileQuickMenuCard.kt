package com.smtm.pickle.presentation.mypage.components.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.PickleCard
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun ProfileQuickMenuCard(
    modifier: Modifier = Modifier,
    onMyJuryClick: () -> Unit,
    onMyBadgeClick: () -> Unit,
) {
    PickleCard(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(0.dp),
        color = PickleTheme.colors.gray50
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CardButton(
                title = "내 배심원",
                icon = R.drawable.ic_mypage_jury,
                onClick = onMyJuryClick
            )
            VerticalDivider(
                modifier = Modifier.height(24.dp),
                thickness = 1.dp,
                color = PickleTheme.colors.gray200
            )
            CardButton(
                title = "내 배지",
                icon = R.drawable.ic_mypage_badge,
                onClick = onMyBadgeClick
            )
        }
    }
}

@Composable
private fun RowScope.CardButton(
    title: String,
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .weight(1f)
            .height(64.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = title,
            style = PickleTheme.typography.body2Medium,
            color = PickleTheme.colors.gray800,
        )
    }
}

@Preview
@Composable
private fun ProfileQuickMenuCardPreview() {
    PickleTheme {
        ProfileQuickMenuCard(
            onMyJuryClick = {},
            onMyBadgeClick = {}
        )
    }
}
