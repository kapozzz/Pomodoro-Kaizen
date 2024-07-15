package com.kapozzz.tasks.screens.list_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.kapozzz.common.ui_acrh.UiEffect
import com.kapozzz.common.ui_acrh.UiEvent
import com.kapozzz.common.ui_acrh.UiState
import com.kapozzz.domain.models.Task

@Stable
data class ListScreenState(
    val actualTasksList: MutableState<List<Task>>,
    val completedTasksList: MutableState<List<Task>>
): UiState {
    companion object {
        fun getDefault(): ListScreenState {
            return ListScreenState(
                actualTasksList = mutableStateOf(emptyList()),
                completedTasksList = mutableStateOf(emptyList())
            )
        }
    }
}
sealed class ListScreenEvent: UiEvent {
    data class OnTaskTap(val id: String): ListScreenEvent()
    data class OnTaskLongTap(val id: String): ListScreenEvent()
    data class DeleteTask(val id: String): ListScreenEvent()
    data object CreateTask: ListScreenEvent()
}

sealed class ListScreenEffect: UiEffect {
    data class OnTaskTap(val id: String): ListScreenEffect()
    data class OnTaskLongTap(val id: String): ListScreenEffect()
    data object CreateTask: ListScreenEffect()
}