package com.smtm.pickle.presentation.designsystem.components.appbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

@Composable
fun PickleBackAppBar(
    onBack: () -> Unit,
    containerColor: Color = PickleTheme.colors.base0,
) {
    PickleAppBarLayout(
        containerColor = containerColor,
        start = { PickleAppBarBackButton(onClick = onBack) },
    )
}

@Preview(
    name = "PickleBackAppBar",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PickleBackAppBarPreview() {
    PickleTheme {
        PickleBackAppBar(
            onBack = {},
        )
    }
}