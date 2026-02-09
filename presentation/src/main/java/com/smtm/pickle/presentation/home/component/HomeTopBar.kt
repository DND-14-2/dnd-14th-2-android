package com.smtm.pickle.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.PickleLogo
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    onStatisticsClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .background(PickleTheme.colors.base0)
            .fillMaxWidth()
            .height(Dimensions.appbarHeight)
            .padding(start = 16.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PickleLogo(width = 58.dp, height = 28.dp)

        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = onStatisticsClick,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_appbar_statistics),
                contentDescription = "statistics",
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified,
            )
        }
    }
}

@Preview(
    name = "HomeTopBar",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun HomeTopBarPreview() {
    PickleTheme {
        HomeTopBar(
            onStatisticsClick = {}
        )
    }
}