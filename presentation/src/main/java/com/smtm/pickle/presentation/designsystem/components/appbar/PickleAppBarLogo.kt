package com.smtm.pickle.presentation.designsystem.components.appbar

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.designsystem.components.PickleLogo
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
internal fun PickleAppBarLogo() {
    PickleLogo(
        modifier = Modifier
            .size(width = Dimensions.appBarLogoWidth, height = Dimensions.appBarLogoHeight)
    )
}

@Preview(
    name = "PickleAppBarLogo",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleAppBarLogoPreview() {
    PickleTheme {
        PickleAppBarLogo()
    }
}
