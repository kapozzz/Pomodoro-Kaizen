package com.kapozzz.timer.presentation.model

abstract class PomodoroStage() {

    abstract val minutes: Int



}

class WorkPomodoro(override val minutes: Int = 2): PomodoroStage()
class RestPomodoro(override val minutes: Int = 1): PomodoroStage()
class LongRestPomodoro(override val minutes: Int = 3): PomodoroStage()