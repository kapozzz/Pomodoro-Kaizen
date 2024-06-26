package com.kapozzz.timer.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.kapozzz.common.ui_acrh.UiEffect
import com.kapozzz.common.ui_acrh.UiEvent
import com.kapozzz.common.ui_acrh.UiState
import com.kapozzz.domain.models.Step
import com.kapozzz.domain.models.Task
import com.kapozzz.domain.models.Tomato

@Stable
data class TimerState(
    val task: MutableState<Task>,
    val step: MutableState<Step>,
    val stepIndex: MutableState<Int>,
    val tomato: MutableState<Tomato>,
    val seconds: MutableState<Int>,
    val minutes: MutableState<Int>,
    val percentage: MutableState<Float>,
    val isWorking: MutableState<Boolean>,
    val steps: MutableState<List<Step>>,
    val notificationsIsEnabled: MutableState<Boolean>,
    val isCompleted: MutableState<Boolean>
) : UiState {
    companion object {
        fun getDefault(): TimerState {
            return TimerState(
                task = mutableStateOf(Task.getDefault()),
                step = mutableStateOf(Step.getDefault()),
                stepIndex = mutableIntStateOf(0),
                tomato = mutableStateOf(Tomato.Work),
                seconds = mutableIntStateOf(0),
                minutes = mutableIntStateOf(0),
                percentage = mutableFloatStateOf(0f),
                isWorking = mutableStateOf(false),
                steps = mutableStateOf(listOf(Step.getDefault())),
                notificationsIsEnabled = mutableStateOf(true),
                isCompleted = mutableStateOf(false)
            )
        }
    }
}

sealed class TimerEvent : UiEvent {
    data object ResetTimer : TimerEvent()
    data object SwitchTimer: TimerEvent()
    data class SwitchNotifications(val enabled: Boolean): TimerEvent()
    data object OnBackClick: TimerEvent()
}

sealed class TimerEffect : UiEffect {
    data object TimeExpires : TimerEffect()
    data object TimerIsStarted: TimerEffect()
    data object OnBackClick: TimerEffect()
}

