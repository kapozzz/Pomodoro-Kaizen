package com.kapozzz.tasks.screens.create_task_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import com.kapozzz.common.ui_acrh.UiEffect
import com.kapozzz.common.ui_acrh.UiEvent
import com.kapozzz.common.ui_acrh.UiState
import com.kapozzz.domain.models.Step
import com.kapozzz.domain.models.TomatoProgram

data class CreateTaskState(
    val name: MutableState<String>,
    val deadline: MutableState<Long>,
    val steps: MutableState<List<Step>>,
    val program: MutableState<TomatoProgram>
): UiState {
    companion object {
        fun getDefault(): CreateTaskState {
            return CreateTaskState(
                name = mutableStateOf(""),
                deadline = mutableLongStateOf(0L),
                steps = mutableStateOf(emptyList()),
                program = mutableStateOf(TomatoProgram.DefaultProgram)
            )
        }
    }
}

sealed class CreateTaskEvent: UiEvent {

}

sealed class CreateTaskEffect: UiEffect {

}

