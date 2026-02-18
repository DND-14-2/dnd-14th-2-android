package com.smtm.pickle.presentation.verdict.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun EmptyVerdictContent(modifier: Modifier = Modifier, selectedTabIndex: Int) {
    val iconRes = if (selectedTabIndex == 0) R.drawable.illust_verdict_balances else R.drawable.illust_verdict_gavel
    val text = if (selectedTabIndex == 0) "아직 내 소비 심판이 없어요" else "아직 내 소비 판결이 없어요"

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = text,
            style = PickleTheme.typography.body3Regular,
            color = PickleTheme.colors.gray600,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyVerdictContentPreview() {
    PickleTheme {
        EmptyVerdictContent(selectedTabIndex = 0)
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyVerdictContentPreview2() {
    PickleTheme {
        EmptyVerdictContent(selectedTabIndex = 1)
    }
}
