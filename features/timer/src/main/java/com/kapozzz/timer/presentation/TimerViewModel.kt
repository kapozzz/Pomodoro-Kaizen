package com.kapozzz.timer.presentation

import com.kapozzz.common.ui_acrh.BaseViewModel
import com.kapozzz.timer.presentation.model.LongRestPomodoro
import com.kapozzz.timer.presentation.model.RestPomodoro
import com.kapozzz.timer.presentation.model.WorkPomodoro
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor() : BaseViewModel<TimerEvent, TimerState, TimerEffect>() {

    private var timerJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun createInitialState(): TimerState = TimerState.getDefault()

    override fun handleEvent(event: TimerEvent) {
        when (event) {
            TimerEvent.ResetTimer -> {
                resetTimer()
            }

            TimerEvent.SwitchTimer -> {
                if (currentState.isWorking.value) stopTimer() else startTimer()
            }
        }
    }

    private fun resetCycle() {

    }

    private fun resetTimer() {
        stopTimer()
        val default = TimerState.getDefault()
        with(currentState) {
            minutes.value = default.minutes.value
            seconds.value = default.seconds.value
            percentage.value = default.percentage.value
            isWorking.value = default.isWorking.value
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        currentState.isWorking.value = false
    }

    private fun startTimer() {
        currentState.isWorking.value = true
        with(currentState) {
            timerJob = scope.launch {

                if (!wasStarted.value) {
                    minutes.value = pomodoroStage.value.minutes
                }
                wasStarted.value = true
                while (minutes.value > 0) {
                    if (seconds.value == 0) {
                        seconds.value = 59
                        minutes.value -= 1
                    }
                    while (seconds.value > 0) {
                        delay(1000L)
                        seconds.value -= 1
                        percentage.value =
                            (((minutes.value * 59) + seconds.value) * 1f) / (pomodoroStage.value.minutes * 59)
                    }
                }
                isWorking.value = false
                newStage()
                if (currentState.stage.value != 15) startTimer()
            }
        }
    }

    private fun newStage() {
        currentState.stage.value += 1
        currentState.wasStarted.value = false
        currentState.pomodoroStage.value = when (currentState.stage.value) {
            1 -> {
                WorkPomodoro()
            }

            2 -> {
                RestPomodoro()
            }

            3 -> {
                WorkPomodoro()
            }

            4 -> {
                RestPomodoro()
            }

            5 -> {
                WorkPomodoro()
            }

            6 -> {
                RestPomodoro()
            }

            7 -> {
                WorkPomodoro()
            }

            8 -> {
                LongRestPomodoro()
            }

            9 -> {
                WorkPomodoro()
            }

            10 -> {
                RestPomodoro()
            }

            11 -> {
                WorkPomodoro()
            }

            12 -> {
                RestPomodoro()
            }

            13 -> {
                WorkPomodoro()
            }

            14 -> {
                RestPomodoro()
            }

            15 -> {
                WorkPomodoro()
            }

            else -> {
                currentState.stage.value = 1
                WorkPomodoro()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

}