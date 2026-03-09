package com.smtm.pickle.presentation.designsystem.components.appbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.designsystem.components.PickleLogo
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun PickleAppBarLogo() {
    PickleLogo()
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
