package com.kapozzz.tasks.screens.create_task_screen

import android.util.Log
import com.kapozzz.common.ui_acrh.BaseViewModel
import com.kapozzz.domain.models.Step
import com.kapozzz.domain.models.Task
import com.kapozzz.domain.repositories.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
) : BaseViewModel<CreateTaskEvent, CreateTaskState, CreateTaskEffect>() {

    override fun createInitialState(): CreateTaskState {
        return CreateTaskState.getDefault()
    }

    override fun handleEvent(event: CreateTaskEvent) {
        when (event) {
            is CreateTaskEvent.AddNewStep -> {
                addNewStep()
            }

            is CreateTaskEvent.ChangeStep -> {
                changeStep(event.index)
            }

            is CreateTaskEvent.SaveStep -> {
                saveStep(dismiss = event.dismiss)
            }

            is CreateTaskEvent.DeleteStep -> {
                deleteStep(event.index)
            }

            is CreateTaskEvent.SaveTask -> {
                validateTaskAndSave()
            }

            is CreateTaskEvent.Back -> {
                setEffect(CreateTaskEffect.Back)
            }
        }
    }

    private fun validateTaskAndSave() {
        with(currentState) {
            when {
                name.value.trim().isEmpty() -> {
                    setEffect(CreateTaskEffect.ShowMessage(CreateTaskMessage.EmptyName))
                }
                steps.value.isEmpty() -> {
                    setEffect(CreateTaskEffect.ShowMessage(CreateTaskMessage.EmptySteps))
                }
                else -> {
                    try {
                        val task = Task(
                            id = Task.generateId(),
                            name = name.value,
                            steps = steps.value,
                            program = program.value,
                            deadline = deadline.value
                        )
                        tasksRepository.addTask(task)
                        setEffect(CreateTaskEffect.ShowMessage(CreateTaskMessage.SaveComplete))
                        setEffect(CreateTaskEffect.Back)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        setEffect(CreateTaskEffect.ShowMessage(CreateTaskMessage.UnknownError))
                    }
                }
            }
        }
    }

    private fun deleteStep(index: Int) {
        with(currentState) {
            val newStepsList = steps.value.toMutableList()
            newStepsList.removeAt(index)
            steps.value = newStepsList.toList()
        }
    }

    private fun resetStep() {
        with(currentState) {
            currentStepTitle.value = ""
            currentStepDescription.value = ""
            currentStateIsCompleted.value = false
            currentStepIndex.value = if (steps.value.isEmpty()) 0 else steps.value.size
        }
    }

    private fun addNewStep() {
        resetStep()
    }

    private fun changeStep(index: Int) {
        try {
            val step = currentState.steps.value[index]
            with(currentState) {
                currentStepTitle.value = step.title
                currentStepDescription.value = step.description
                currentStepIndex.value = index
            }
            currentState.bottomSheetIsVisible.value = true
        } catch (e: Exception) {
            Log.e("PomodoroKaizen", "Empty step")
        }
    }

    private fun saveStep(dismiss: Boolean) {
        if (!dismiss) {
            with(currentState) {
                val step = Step(
                    title = currentStepTitle.value,
                    description = currentStepDescription.value,
                    isCompleted = currentStateIsCompleted.value
                )
                val newStepsList = steps.value.toMutableList()
                if (steps.value.isNotEmpty() && currentStepIndex.value < steps.value.size) {
                    newStepsList.removeAt(currentStepIndex.value)
                }
                newStepsList.add(currentStepIndex.value, step)
                steps.value = newStepsList.toList()
                currentState.bottomSheetIsVisible.value = false
                resetStep()
            }
        }
    }
}