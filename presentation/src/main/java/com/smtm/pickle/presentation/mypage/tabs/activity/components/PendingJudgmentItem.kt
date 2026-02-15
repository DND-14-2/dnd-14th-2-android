package com.smtm.pickle.presentation.mypage.tabs.activity.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun PendingJudgmentItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    title: String,
    price: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(Dimensions.iconLarge)
                .clip(CircleShape)
                .background(PickleTheme.colors.gray50)
                .border(0.5.dp, PickleTheme.colors.gray200, CircleShape)
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = title,
                style = PickleTheme.typography.body2Medium,
                color = PickleTheme.colors.gray700
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = price,
                style = PickleTheme.typography.body2Medium,
                color = PickleTheme.colors.gray700
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onClick,
            modifier = Modifier.size(69.dp, 32.dp),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PickleTheme.colors.primary50,
            )
        ) {
            Text(
                text = "심판하기",
                style = PickleTheme.typography.body4Medium,
                color = PickleTheme.colors.primary500
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PendingJudgmentItemPreview() {
    PickleTheme {
        PendingJudgmentItem(
            iconRes = R.drawable.ic_mypage_coin,
            title = "식비",
            price = "10,000원",
            onClick = {}
        )
    }
}
