package com.smtm.pickle.presentation.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun PickleLogo(
    modifier: Modifier = Modifier,
    size: Dp = 200.dp
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // TODO: 추후 디자인 정해지면 비율 조정
        val iconSize = size * 0.8f

        Icon(
            // TODO: 앱 로고로 변경
            painter = painterResource(R.drawable.icon_kakao),
            contentDescription = "Pickle 로고",
            modifier = Modifier.size(iconSize),
            tint = Color.Unspecified
        )
    }
}


@Preview
@Composable
private fun PickleLogoPreview() {
    PickleTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PickleLogo()
        }
    }
}
