package com.smtm.pickle.presentation.designsystem.components.button.model

import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme

enum class PickleButtonType {
    Primary,
    Secondary,
    Tertiary,
    Ghost,
}

@Composable
fun PickleButtonType.toColors(): ButtonColors {
    val colors = PickleTheme.colors
    return when (this) {
        PickleButtonType.Primary -> ButtonColors(
            containerColor = colors.primary400,
            contentColor = colors.base0,
            disabledContainerColor = colors.gray100,
            disabledContentColor = colors.gray600,
        )

        PickleButtonType.Secondary -> ButtonColors(
            containerColor = colors.gray100,
            contentColor = colors.gray600,
            disabledContainerColor = colors.gray100,
            disabledContentColor = colors.gray400,
        )

        PickleButtonType.Tertiary -> ButtonColors(
            containerColor = colors.primary50,
            contentColor = colors.primary500,
            disabledContainerColor = colors.gray100,
            disabledContentColor = colors.gray600,
        )

        PickleButtonType.Ghost -> ButtonColors(
            containerColor = colors.base0,
            contentColor = colors.gray500,
            disabledContainerColor = colors.base0,
            disabledContentColor = colors.gray400,
        )
    }
}
