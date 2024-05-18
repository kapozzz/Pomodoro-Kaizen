package com.kapozzz.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.kapozzz.pomodoro_kaizen.ui.theme.AppTypo

@Composable
fun AppTheme(
    colorStyle: ColorStyle = ColorStyle.Base,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    when (colorStyle) {
        ColorStyle.Base -> baseLightPalette
    }

    CompositionLocalProvider(
        LocalColors provides baseLightPalette,
        LocalTypo provides AppTypo,
        content = content
    )
}