package com.kapozzz.common.extentions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toFormattedDateString(pattern: String = "dd/MM/yyyy", locale: Locale = Locale.getDefault()): String {
    val date = Date(this)
    val dateFormat = SimpleDateFormat(pattern, locale)
    return dateFormat.format(date)
}