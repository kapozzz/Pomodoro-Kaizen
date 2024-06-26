package com.kapozzz.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.kapozzz.ui.type.AppTypo

@Composable
fun AppTheme(
    colorStyle: ColorStyle = ColorStyle.Base,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val palette = when (colorStyle) {
        ColorStyle.Base -> baseLightPalette
    }

    CompositionLocalProvider(
        LocalColors provides palette,
        LocalTypo provides AppTypo,
        content = content
    )
    MaterialTheme {

    }
}