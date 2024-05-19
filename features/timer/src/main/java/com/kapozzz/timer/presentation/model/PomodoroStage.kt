package com.kapozzz.timer.presentation.model

abstract class PomodoroStage() {

    abstract val minutes: Int



}

class WorkPomodoro(override val minutes: Int = 25): PomodoroStage()
class RestPomodoro(override val minutes: Int = 5): PomodoroStage()
class LongRestPomodoro(override val minutes: Int = 30): PomodoroStage()