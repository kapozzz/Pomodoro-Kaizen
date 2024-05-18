package com.kapozzz.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.kapozzz.pomodoro_kaizen.ui.theme.AppTypo

data class Colors(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,
    val background: Color,
    val onBackground: Color,
    val hint: Color,
    val outline: Color,
    val error: Color
)

enum class ColorStyle() {
    Base
}

object AppTheme {
    val colors: Colors
        @Composable
        get() = LocalColors.current

    val typo: AppTypo
        @Composable
        get() = LocalTypo.current
}

internal val LocalColors = staticCompositionLocalOf<Colors> {
    error("No colors provided")
}

internal val LocalTypo = staticCompositionLocalOf<AppTypo> {
    error("Typo not provided")
}
