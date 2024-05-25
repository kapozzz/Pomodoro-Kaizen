package com.kapozzz.timer.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kapozzz.common.ui_acrh.BaseViewModel
import com.kapozzz.timer.presentation.model.PomodoroStage
import com.kapozzz.timer.util.TimerNotificationService
import com.kapozzz.timer.util.TimerReceiver
import com.kapozzz.timer.util.calculatePercentage
import com.kapozzz.timer.util.timeToString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val notificationService: TimerNotificationService
) : BaseViewModel<TimerEvent, TimerState, TimerEffect>() {

    private var timerJob: Job? = null
    private val timerScope = CoroutineScope(Dispatchers.IO)
    private var wasStarted: Boolean = false
    override fun createInitialState(): TimerState = TimerState.getDefault()

    override fun handleEvent(event: TimerEvent) {
        when (event) {
            is TimerEvent.ResetTimer -> {
                resetTimer()
            }
            is TimerEvent.SwitchTimer -> {
                if (currentState.isWorking.value) stopTimer() else startTimer()
            }
            is TimerEvent.SwitchNotifications -> {
                currentState.notificationsIsEnabled.value = event.enabled
            }
        }
    }

    private var currentStage: PomodoroStage =currentState.program.value[currentState.stage.value]

        init {
        viewModelScope.launch {
            subscribeToReceiver()
        }
    }

    private suspend fun subscribeToReceiver() {
        TimerReceiver.flow.collect {
            handleEvent(it)
            Log.d("NEW", "STATE")
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
    }

    private fun stopTimer() {
        currentState.isWorking.value = false
        updateNotificationTime()
    }

    private fun startTimer() {
        setEffect(TimerEffect.TimerIsStarted)
        with(currentState) {
            isWorking.value = true
            timerJob?.cancel()
            timerJob = timerScope.launch {
                if (!wasStarted) {
                    minutes.value = currentStage.minutes
                    wasStarted = true
                }
                while ((minutes.value > 0 || seconds.value > 0) && isWorking.value) {
                    if (seconds.value == 0) {
                        minutes.value -= 1
                        seconds.value += 59
                    }
                    updateNotificationTime()
                    while (seconds.value > 0 && isWorking.value) {
                        seconds.value -= 1
                        currentState.calculatePercentage()
                        updateNotificationTime()
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

    private fun updateNotificationTime() {
        if (!currentState.notificationsIsEnabled.value) return
        notificationService.updateTime(currentState.timeToString(), currentState.isWorking.value)
    }

    private fun newStage() {
        wasStarted = false
        resetTimer()
        currentState.stage.value += 1
        if (currentState.stage.value > currentState.program.value.size) {
            setEffect(TimerEffect.TimeExpires)
        } else {
            currentStage = currentState.program.value[currentState.stage.value]
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

}