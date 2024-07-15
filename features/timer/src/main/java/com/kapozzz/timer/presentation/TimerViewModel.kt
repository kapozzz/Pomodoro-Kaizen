package com.kapozzz.timer.presentation

import androidx.lifecycle.viewModelScope
import com.kapozzz.common.ui_acrh.BaseViewModel
import com.kapozzz.domain.models.Tomato
import com.kapozzz.domain.repositories.TasksRepository
import com.kapozzz.timer.util.TimerNotificationService
import com.kapozzz.timer.util.TimerReceiver
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
    private val notificationService: TimerNotificationService,
    private val tasksRepository: TasksRepository
) : BaseViewModel<TimerEvent, TimerState, TimerEffect>() {

    private var timerJob: Job? = null
    private val timerScope = CoroutineScope(Dispatchers.IO)
    private var wasStarted: Boolean = false
    private var currentCycleTomatoIndex = 0

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

            is TimerEvent.OnBackClick -> {
                onBackClick()
            }
        }
    }

    private suspend fun subscribeToReceiver() {
        TimerReceiver.flow.collect {
            handleEvent(it)
        }
    }

    init {
        viewModelScope.launch {
            subscribeToReceiver()
        }
    }

    fun initializeTask(id: String) {
        viewModelScope.launch {
            val task = tasksRepository.getTaskById(id)
            with(currentState) {
                this.task.value = task
                steps.value = task.steps
            }
            nextStep()
        }
    }

    private fun onBackClick() {
        setEffect(TimerEffect.OnBackClick)
    }

    private fun startTimer() {
        setEffect(TimerEffect.TimerIsStarted)
        with(currentState) {
            timerJob?.cancel()
            timerJob = timerScope.launch {
                isWorking.value = true
                if (!wasStarted) {
                    minutes.value = getStepMinutes()
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
                        calculatePercentage()
                        updateNotificationTime()
                        delay(1000L)
                    }
                }
                if (isWorking.value && wasStarted && minutes.value == 0 && seconds.value == 0) {
                    isWorking.value = false
                    newStep()
                    // начинать следующий шаг лучше по нажатию пользователя
                    startTimer()
                }
            }
        }
    }

    private fun stopTimer() {
        currentState.isWorking.value = false
        updateNotificationTime()
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

    private fun newStep() {
        resetTimer()
        if (currentState.tomato.value == Tomato.Work) {
            with(currentState.steps.value) {
                get(currentState.stepIndex.value).isCompleted = true
            }
        }
        nextStep()
        isNextStepExist()
        if (currentCycleTomatoIndex == 7) currentCycleTomatoIndex = 0 else currentCycleTomatoIndex++
        currentState.tomato.value = when (currentCycleTomatoIndex) {
            0 -> Tomato.Work
            1 -> Tomato.Rest
            2 -> Tomato.Work
            3 -> Tomato.Rest
            4 -> Tomato.Work
            5 -> Tomato.Rest
            6 -> Tomato.Work
            7 -> Tomato.LongRest
            else -> error("tomato index > 7")
        }
    }

    private fun taskComplete() {
        currentState.isCompleted.value = true
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.addTask(currentState.task.value)
        }
    }

    private fun isNextStepExist() {
        val nextStep = currentState.steps.value.firstOrNull { !it.isCompleted }
        if (nextStep == null) {
            taskComplete()
        }
    }

    private fun nextStep() {
        with(currentState) {
            val nextStep = steps.value.firstOrNull { !it.isCompleted }
            if (nextStep == null) {
                taskComplete()
            } else {
                val index = steps.value.indexOfFirst { !it.isCompleted }
                stepIndex.value = index
                step.value = nextStep
            }
        }
    }

    private fun getStepMinutes(): Int {
        return with(currentState.task.value.program) {
            when (currentState.tomato.value) {
                Tomato.Work -> workTime
                Tomato.Rest -> restTime
                Tomato.LongRest -> longRestTime
            }
        }
    }

    private fun calculatePercentage() {
        with(currentState) {
            percentage.value =
                (((minutes.value * 59) + seconds.value) * 1f) / (getStepMinutes() * 59)
        }
    }

    private fun updateNotificationTime() {
        if (!currentState.notificationsIsEnabled.value) return
        notificationService.updateTime(currentState.timeToString(), currentState.isWorking.value)
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}