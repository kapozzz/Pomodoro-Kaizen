package com.kapozzz.pomodoro_kaizen.navigation

import com.kapozzz.tasks.navigation.TasksApi
import com.kapozzz.timer.navigation.TimerApi

data class NavigationProvider(
    val timer: TimerApi,
    val tasks: TasksApi
)