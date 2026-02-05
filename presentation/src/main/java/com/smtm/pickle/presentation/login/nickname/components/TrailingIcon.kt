package com.smtm.pickle.presentation.login.nickname.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun TrailingIcon(iconRes: Int) {
    Icon(
        painter = painterResource(iconRes),
        contentDescription = null,
        tint = Color.Unspecified,
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .size(Dimensions.iconMedium)
    )
}
