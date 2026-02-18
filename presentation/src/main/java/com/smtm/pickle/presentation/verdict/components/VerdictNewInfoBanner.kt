package com.smtm.pickle.presentation.verdict.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun VerdictNewInfoBanner(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.illust_verdict_news),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "새로운 소식이 있어요",
                    style = PickleTheme.typography.body1Bold,
                    color = PickleTheme.colors.gray800
                )
                Image(
                    painter = painterResource(R.drawable.ic_common_new),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "지금 바로 확인해 보세요",
                style = PickleTheme.typography.body4Medium,
                color = PickleTheme.colors.gray600
            )
        }
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = PickleTheme.colors.primary50,
                contentColor = PickleTheme.colors.primary500
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.size(45.dp, 32.dp)
        ) {
            Text(
                text = "보기",
                style = PickleTheme.typography.body4Medium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VerdictNewInfoBannerPreview() {
    PickleTheme {
        VerdictNewInfoBanner()
    }
}
