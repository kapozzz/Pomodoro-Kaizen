package com.kapozzz.timer.presentation

import com.kapozzz.common.ui_acrh.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(): BaseViewModel<TimerEvent, TimerState, TimerEffect>() {

    private var timerJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun createInitialState(): TimerState = TimerState.getDefault()

    override fun handleEvent(event: TimerEvent) {
        when (event) {
            TimerEvent.StartTimer -> {
                startTimer()
            }
            TimerEvent.ResetTimer -> TODO()
            TimerEvent.StopTimer -> TODO()
        }
    }

    private fun startTimer() {
        with(currentState) {
            timerMinutes.value = 25
            timerIsStarted.value = true
        }
        timerJob?.cancel()
        timerJob = scope.launch {
            start()
        }
    }

    private suspend fun start() {
        with(currentState) {
            while (timerMinutes.value > 0) {
                timerSeconds.value = 60
                while (timerSeconds.value > 0) {
                    delay(1000L)
                    timerSeconds.value -= 1
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

}