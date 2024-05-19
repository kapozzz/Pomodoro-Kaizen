package com.kapozzz.timer.presentation

import com.kapozzz.common.notifications.PomodoroProgressNotification
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
class TimerViewModel @Inject constructor(
    private val progressNotification: PomodoroProgressNotification
) : BaseViewModel<TimerEvent, TimerState, TimerEffect>() {

    private var timerJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO)
    private var wasStarted: Boolean = false

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

    private fun resetTimer() {
        stopTimer()
        with(currentState) {
            minutes.value = 0
            seconds.value = 0
            percentage.value = 0f
        }
        wasStarted = false
        startTimer()
    }

    private fun stopTimer() {
        currentState.isWorking.value = false
    }

    private fun startTimer() {
        with(currentState) {
            isWorking.value = true
            timerJob?.cancel()
            timerJob = scope.launch {
                if (!wasStarted) {
                    minutes.value = pomodoroStage.value.minutes
                    wasStarted = true
                }
                while ((minutes.value > 0 || seconds.value > 0) && isWorking.value) {
                    if (seconds.value == 0) {
                        minutes.value -= 1
                        seconds.value += 59
                    }
                    showProgressInNotification()
                    while (seconds.value > 0 && isWorking.value) {
                        seconds.value -= 1
                        calculatePercentage()
                        showProgressInNotification()
                        delay(1000L)
                    }
                }
                if (isWorking.value && wasStarted && minutes.value == 0 && seconds.value == 0) {
                    isWorking.value = false
                    newStage()
                    startTimer()
                }
            }
        }
    }

    private fun calculatePercentage() {
        with(currentState) {
            percentage.value =
                (((minutes.value * 59) + seconds.value) * 1f) / (pomodoroStage.value.minutes * 59)
        }
    }

    private fun showProgressInNotification() {
        with(currentState) {
            progressNotification.showNotification("${minutes.value}:${if (seconds.value < 10) "0" else ""}${seconds.value}")
        }
    }

    private fun newStage() {
        currentState.stage.value += 1
        wasStarted = false
        resetTimer()
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