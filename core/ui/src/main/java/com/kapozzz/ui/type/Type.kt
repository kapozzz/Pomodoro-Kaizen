package com.kapozzz.pomodoro_kaizen.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kapozzz.ui.type.aBeeZeeFontsFamily
import com.kapozzz.ui.type.acmeFontFamily

object AppTypo {

    val mediumTitle = TextStyle(
        fontFamily = acmeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    )

    val mediumBody = TextStyle(
        fontFamily = aBeeZeeFontsFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp
    )

    val smallBody = TextStyle(
        fontFamily = acmeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    )

}