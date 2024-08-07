package com.kapozzz.tasks.screens.create_task_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import com.kapozzz.common.ui_acrh.UiEffect
import com.kapozzz.common.ui_acrh.UiEvent
import com.kapozzz.common.ui_acrh.UiState
import com.kapozzz.domain.models.Step
import com.kapozzz.domain.models.Task
import com.kapozzz.domain.models.TomatoProgram

data class CreateTaskState(
    val id: MutableState<String>,
    val name: MutableState<String>,
    val deadline: MutableState<Long>,
    val steps: MutableState<List<Step>>,
    val program: MutableState<TomatoProgram>,
    val programs: MutableState<List<TomatoProgram>>,
    val currentStepTitle: MutableState<String>,
    val currentStepDescription: MutableState<String>,
    val currentStateIsCompleted: MutableState<Boolean>,
    val currentStepIndex: MutableState<Int>,
    val bottomSheetIsVisible: MutableState<Boolean>,
    val settingsIsVisible: MutableState<Boolean>,
    val taskId: MutableState<String?>
) : UiState {
    companion object {
        fun getDefault(): CreateTaskState {
            return CreateTaskState(
                id = mutableStateOf(Task.generateId()),
                name = mutableStateOf(""),
                deadline = mutableLongStateOf(0L),
                steps = mutableStateOf(emptyList()),
                program = mutableStateOf(TomatoProgram.defaultProgram),
                programs = mutableStateOf(emptyList()),
                currentStepTitle = mutableStateOf(""),
                currentStepDescription = mutableStateOf(""),
                currentStateIsCompleted = mutableStateOf(false),
                currentStepIndex = mutableIntStateOf(0),
                bottomSheetIsVisible = mutableStateOf(false),
                settingsIsVisible = mutableStateOf(false),
                taskId = mutableStateOf(null)
            )
        }
    }
}

sealed class CreateTaskEvent : UiEvent {
    data class SaveStep(val dismiss: Boolean = false) : CreateTaskEvent()
    data class ChangeStep(val index: Int) : CreateTaskEvent()
    data class DeleteStep(val index: Int) : CreateTaskEvent()
    data class DeleteTomatoProgram(val program: TomatoProgram) : CreateTaskEvent()
    data class SaveTomatoProgram(val program: TomatoProgram): CreateTaskEvent()
    data class ApplyTomatoProgram(val program: TomatoProgram): CreateTaskEvent()
    data object AddNewStep : CreateTaskEvent()
    data object SaveTask : CreateTaskEvent()
    data object Back : CreateTaskEvent()
    data object DeleteTask : CreateTaskEvent()
}

sealed class CreateTaskEffect : UiEffect {
    data class ShowMessage(val type: CreateTaskMessage) : CreateTaskEffect()
    data object SaveComplete : CreateTaskEffect()
    data object Back : CreateTaskEffect()
}

enum class CreateTaskMessage {
    EmptyName, EmptySteps, UnknownError
}

