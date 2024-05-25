package com.kapozzz.timer.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.kapozzz.common.ui_acrh.UiEffect
import com.kapozzz.common.ui_acrh.UiEvent
import com.kapozzz.common.ui_acrh.UiState
import com.kapozzz.timer.presentation.model.PomodoroStage

data class TimerState(
    val stage: MutableState<Int>,
    val seconds: MutableState<Int>,
    val minutes: MutableState<Int>,
    val percentage: MutableState<Float>,
    val isWorking: MutableState<Boolean>,
    val program: MutableState<List<PomodoroStage>>,
    val notificationsIsEnabled: MutableState<Boolean>
) : UiState {
    companion object {
        fun getDefault(): TimerState {
            return TimerState(
                stage = mutableIntStateOf(0),
                seconds = mutableIntStateOf(0),
                minutes = mutableIntStateOf(0),
                percentage = mutableFloatStateOf(0f),
                isWorking = mutableStateOf(false),
                program = mutableStateOf(PomodoroStage.defaultProgram),
                notificationsIsEnabled = mutableStateOf(true)
            )
        }
    }
}

sealed class TimerEvent : UiEvent {
    data object ResetTimer : TimerEvent()
    data object SwitchTimer: TimerEvent()
    data class SwitchNotifications(val enabled: Boolean): TimerEvent()
}

sealed class TimerEffect : UiEffect {

    data object TimeExpires : TimerEffect()
    data object TimerIsStarted: TimerEffect()

}