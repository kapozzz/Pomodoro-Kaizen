package com.kapozzz.tasks.screens.list_screen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kapozzz.common.ui_acrh.BaseViewModel
import com.kapozzz.domain.repositories.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
) : BaseViewModel<ListScreenEvent, ListScreenState, ListScreenEffect>() {
    override fun createInitialState(): ListScreenState {
        return ListScreenState.getDefault()
    }

    init {
        viewModelScope.launch {
            subscribeOnTasks()
        }
    }

    private suspend fun subscribeOnTasks() {
        tasksRepository.getTasks()
            .flowOn(Dispatchers.IO)
            .collect {
                Log.e("LIST", "$it")
                currentState.list.value = it
            }
    }

    override fun handleEvent(event: ListScreenEvent) {
        when (event) {
            is ListScreenEvent.CreateTask -> setEffect(ListScreenEffect.CreateTask)
            is ListScreenEvent.OnTaskTap -> setEffect(ListScreenEffect.OnTaskTap(event.id))
            is ListScreenEvent.OnTaskLongTap -> setEffect(ListScreenEffect.OnTaskLongTap(event.id))
        }
    }
}