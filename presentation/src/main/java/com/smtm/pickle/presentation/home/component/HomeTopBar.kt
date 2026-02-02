package com.smtm.pickle.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(PickleTheme.colors.base0)
            .fillMaxWidth()
            .height(Dimensions.appbarHeight)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "LOGO")
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
        HomeTopBar()
    }
}