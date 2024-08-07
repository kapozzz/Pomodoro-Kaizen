package com.kapozzz.tasks.screens.create_task_screen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kapozzz.common.ui_acrh.BaseViewModel
import com.kapozzz.domain.models.Step
import com.kapozzz.domain.models.Task
import com.kapozzz.domain.models.TomatoProgram
import com.kapozzz.domain.repositories.TasksRepository
import com.kapozzz.domain.repositories.TomatoProgramsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val tomatoProgramsRepository: TomatoProgramsRepository
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

            is CreateTaskEvent.DeleteTask -> {
                deleteTask()
            }

            is CreateTaskEvent.DeleteTomatoProgram -> {
                deleteTomatoProgram(event.program)
            }

            is CreateTaskEvent.SaveTomatoProgram -> {
                saveTomatoProgram(event.program)
            }

            is CreateTaskEvent.ApplyTomatoProgram -> {
                applyTomatoProgram(event.program)
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.Main) {
            tomatoProgramsRepository
                .getTomatoPrograms()
                .collect {
                    currentState.programs.value = it
                }
        }
    }

    private fun saveTomatoProgram(program: TomatoProgram) {
        viewModelScope.launch(Dispatchers.IO) {
            tomatoProgramsRepository.addTomatoProgram(program)
        }
    }

    private fun applyTomatoProgram(program: TomatoProgram) {
        viewModelScope.launch(Dispatchers.IO) {
            currentState.program.value = program
        }
    }

    private fun deleteTomatoProgram(program: TomatoProgram) {
        viewModelScope.launch(Dispatchers.IO) {
            tomatoProgramsRepository.deleteTomatoProgram(program.id)
        }
    }

    fun initializeTask(id: String) {
        viewModelScope.launch {
            currentState.taskId.value = id
            val task = tasksRepository.getTaskById(id)
            try {
                with(currentState) {
                    name.value = task.name
                    deadline.value = task.deadline
                    steps.value = task.steps
                    this.id.value = task.id
                    program.value = task.program
                    steps.value = task.steps
                }
            } catch (e: NullPointerException) {
                Log.w("Pomodoro Kaizen", "task initialization before delete")
            }
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            currentState.taskId.value?.let {
                setEffect(CreateTaskEffect.Back)
                tasksRepository.deleteTask(it)
            }
        }
    }

    private fun validateTaskAndSave() {
        viewModelScope.launch(Dispatchers.IO) {
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
                                id = id.value,
                                name = name.value,
                                steps = steps.value,
                                program = program.value,
                                deadline = deadline.value
                            )
                            tasksRepository.addTask(task)
                            setEffect(CreateTaskEffect.SaveComplete)
                            setEffect(CreateTaskEffect.Back)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            setEffect(CreateTaskEffect.ShowMessage(CreateTaskMessage.UnknownError))
                        }
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