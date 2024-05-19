package com.kapozzz.timer.presentation.model

import com.kapozzz.timer.util.LONG_REST_TIME
import com.kapozzz.timer.util.REST_TIME
import com.kapozzz.timer.util.WORK_TOMATO_TIME

sealed class PomodoroStage(val minutes: Int) {
    data object WorkPomodoro : PomodoroStage(minutes = WORK_TOMATO_TIME)
    data object RestPomodoro : PomodoroStage(minutes = REST_TIME)
    data object LongRestPomodoro : PomodoroStage(minutes = LONG_REST_TIME)

    companion object {
        val defaultProgram = listOf(
            WorkPomodoro,
            RestPomodoro,
            WorkPomodoro,
            RestPomodoro,
            WorkPomodoro,
            RestPomodoro,
            WorkPomodoro,
            LongRestPomodoro,
            WorkPomodoro,
            RestPomodoro,
            WorkPomodoro,
            RestPomodoro,
            WorkPomodoro,
            RestPomodoro,
            WorkPomodoro
        )
        val fastProgram = listOf(
            WorkPomodoro,
            RestPomodoro,
            LongRestPomodoro,
            WorkPomodoro
        )
    }
}


