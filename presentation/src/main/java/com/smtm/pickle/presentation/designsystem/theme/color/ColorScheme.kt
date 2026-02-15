package com.smtm.pickle.presentation.designsystem.theme.color

import androidx.compose.material3.lightColorScheme

val LightColorScheme = lightColorScheme(
    primary = ColorPalette.primary400, // 기본 버튼 배경
    onPrimary = ColorPalette.base0,
    primaryContainer = ColorPalette.primary400, // FAB
    onPrimaryContainer = ColorPalette.base0,

    secondaryContainer = ColorPalette.gray700,
    onSecondaryContainer = ColorPalette.base0,

    background = ColorPalette.base0, // 화면 전체 배경

    surface = ColorPalette.base0, // 카드 배경
    onSurface = ColorPalette.gray500,
    surfaceVariant = ColorPalette.gray100, // 칩 배경
    onSurfaceVariant = ColorPalette.gray600,

    error = ColorPalette.error50,
    onError = ColorPalette.base0,
    errorContainer = ColorPalette.error100,
    onErrorContainer = ColorPalette.error50,

    outline = ColorPalette.gray300, // 테두리
    outlineVariant = ColorPalette.gray300, // 테두리

    scrim = ColorPalette.base100.copy(alpha = 0.45f), // 다이얼로그 배경
)

val DarkColorScheme = LightColorScheme
