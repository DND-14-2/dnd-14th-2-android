package com.smtm.pickle.presentation.designsystem.theme.color

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class PickleColors(
    val base0: Color,
    val base30: Color,
    val base60: Color,
    val base100: Color,
    val primary50: Color,
    val primary100: Color,
    val primary200: Color,
    val primary300: Color,
    val primary400: Color,
    val primary500: Color,
    val primary600: Color,
    val gray50: Color,
    val gray100: Color,
    val gray200: Color,
    val gray300: Color,
    val gray400: Color,
    val gray500: Color,
    val gray600: Color,
    val gray700: Color,
    val gray800: Color,
    val background50: Color,
    val background100: Color,
    val error50: Color,
    val error100: Color,
    val transparent: Color,
    val black: Color,
)

val LightPickleColors = PickleColors(
    base0 = ColorPalette.base0,
    base30 = ColorPalette.base30,
    base60 = ColorPalette.base60,
    base100 = ColorPalette.base100,
    primary50 = ColorPalette.primary50,
    primary100 = ColorPalette.primary100,
    primary200 = ColorPalette.primary200,
    primary300 = ColorPalette.primary300,
    primary400 = ColorPalette.primary400,
    primary500 = ColorPalette.primary500,
    primary600 = ColorPalette.primary600,
    gray50 = ColorPalette.gray50,
    gray100 = ColorPalette.gray100,
    gray200 = ColorPalette.gray200,
    gray300 = ColorPalette.gray300,
    gray400 = ColorPalette.gray400,
    gray500 = ColorPalette.gray500,
    gray600 = ColorPalette.gray600,
    gray700 = ColorPalette.gray700,
    gray800 = ColorPalette.gray800,
    background50 = ColorPalette.background50,
    background100 = ColorPalette.background100,
    error50 = ColorPalette.error50,
    error100 = ColorPalette.error100,
    transparent = ColorPalette.transparent,
    black = ColorPalette.black,
)

val DarkPickleColors = LightPickleColors

val LocalPickleColors = staticCompositionLocalOf { LightPickleColors }
