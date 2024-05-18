package com.kapozzz.timer.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.kapozzz.common.ui_acrh.UiEffect
import com.kapozzz.common.ui_acrh.UiEvent
import com.kapozzz.common.ui_acrh.UiState

data class TimerState(
    val timerSeconds: MutableState<Int>,
    val timerMinutes: MutableState<Int>,
    val timerPercentage: MutableState<Float>,
    val timerIsStarted: MutableState<Boolean>
) : UiState {
    companion object {
        fun getDefault(): TimerState {
            return TimerState(
                timerSeconds = mutableStateOf(0),
                timerMinutes = mutableStateOf(0),
                timerPercentage = mutableStateOf(0f),
                timerIsStarted = mutableStateOf(false)
            )
        }
    }
}

sealed class TimerEvent : UiEvent {

    data object StartTimer : TimerEvent()
    data object StopTimer : TimerEvent()
    data object ResetTimer : TimerEvent()

}

sealed class TimerEffect : UiEffect {

    data object TimeExpires : TimerEffect()

}