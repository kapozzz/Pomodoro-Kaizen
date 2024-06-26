package com.kapozzz.timer.util

import com.kapozzz.timer.presentation.TimerState

fun TimerState.timeToString(): String {
    with(this) {
        return "${if (minutes.value == 0) "00" else minutes.value}:${if (seconds.value < 10) "0" else ""}${seconds.value}"
    }
}