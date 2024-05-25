package com.kapozzz.tasks.screens.create_task_screen

import com.kapozzz.common.ui_acrh.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor() :
    BaseViewModel<CreateTaskEvent, CreateTaskState, CreateTaskEffect>() {
    override fun createInitialState(): CreateTaskState {
        return CreateTaskState.getDefault()
    }

    override fun handleEvent(event: CreateTaskEvent) {
        TODO("Not yet implemented")
    }
}