package com.smtm.pickle.presentation.designsystem.components.button.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

enum class PickleButtonStyle {
    Primary,
    Secondary,
    Tertiary,
    Ghost,
}

data class PickleButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
)

@Composable
fun PickleButtonStyle.toColors(): PickleButtonColors {
    val colors = PickleTheme.colors
    return when (this) {
        PickleButtonStyle.Primary -> PickleButtonColors(
            containerColor = colors.primary400,
            contentColor = colors.base0,
            disabledContainerColor = colors.gray100,
            disabledContentColor = colors.gray600,
        )

        PickleButtonStyle.Secondary -> PickleButtonColors(
            containerColor = colors.gray100,
            contentColor = colors.gray600,
            disabledContainerColor = colors.gray100,
            disabledContentColor = colors.gray400,
        )

        PickleButtonStyle.Tertiary -> PickleButtonColors(
            containerColor = colors.primary50,
            contentColor = colors.primary500,
            disabledContainerColor = colors.gray100,
            disabledContentColor = colors.gray600,
        )

        PickleButtonStyle.Ghost -> PickleButtonColors(
            containerColor = colors.base0,
            contentColor = colors.gray500,
            disabledContainerColor = colors.base0,
            disabledContentColor = colors.gray400,
        )
    }
}
