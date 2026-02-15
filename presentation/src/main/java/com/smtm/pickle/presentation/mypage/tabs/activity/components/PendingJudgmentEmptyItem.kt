package com.smtm.pickle.presentation.mypage.tabs.activity.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun PendingJudgmentEmptyItem(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .border(
                width = 1.dp,
                color = PickleTheme.colors.gray200,
                shape = RoundedCornerShape(Dimensions.radius)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_snackbar_success),
            contentDescription = null,
            tint = PickleTheme.colors.gray600
        )
        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = "현재 보류하고 있는 심판이 없어요",
            style = PickleTheme.typography.body2Medium,
            color = PickleTheme.colors.gray600
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PendingJudgmentEmptyItemPreview() {
    PickleTheme {
        PendingJudgmentEmptyItem()
    }
}
