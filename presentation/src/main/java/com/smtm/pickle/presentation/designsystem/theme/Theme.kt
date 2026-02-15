package com.smtm.pickle.presentation.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.color.LightColorScheme
import com.smtm.pickle.presentation.designsystem.theme.color.LightPickleColors
import com.smtm.pickle.presentation.designsystem.theme.color.LightSemanticColors
import com.smtm.pickle.presentation.designsystem.theme.color.LocalPickleColors
import com.smtm.pickle.presentation.designsystem.theme.color.LocalSemanticColors
import com.smtm.pickle.presentation.designsystem.theme.color.PickleColors
import com.smtm.pickle.presentation.designsystem.theme.color.SemanticColors
import com.smtm.pickle.presentation.designsystem.theme.typography.DefaultPickleTypography
import com.smtm.pickle.presentation.designsystem.theme.typography.LocalPickleTypography
import com.smtm.pickle.presentation.designsystem.theme.typography.PickleTypography

@Composable
fun PickleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val pickleColors = LightPickleColors
    val semanticColors = LightSemanticColors
    val colorScheme = LightColorScheme
    val typography = DefaultPickleTypography

    CompositionLocalProvider(
        LocalPickleColors provides pickleColors,
        LocalSemanticColors provides semanticColors,
        LocalPickleTypography provides typography,
        LocalMinimumInteractiveComponentSize provides 0.dp,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography(),
            content = content
        )
    }
}

object PickleTheme {
    val colors: PickleColors
        @Composable
        @ReadOnlyComposable
        get() = LocalPickleColors.current

    val semantic: SemanticColors
        @Composable
        @ReadOnlyComposable
        get() = LocalSemanticColors.current

    val typography: PickleTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalPickleTypography.current
}
