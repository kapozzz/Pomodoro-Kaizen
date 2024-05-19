package com.kapozzz.timer.util

import com.kapozzz.timer.presentation.TimerState

fun TimerState.calculatePercentage() {
    with(this) {
        this.percentage.value = (((minutes.value * 59) + seconds.value) * 1f) / (program.value[stage.value].minutes * 59)
    }
}

fun TimerState.timeToString(): String {
    with(this) {
        return "${if (minutes.value == 0) "00" else minutes.value}:${if (seconds.value < 10) "0" else ""}${seconds.value}"
    }
}