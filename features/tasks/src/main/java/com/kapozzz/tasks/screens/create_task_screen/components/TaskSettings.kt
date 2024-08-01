package com.kapozzz.tasks.screens.create_task_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.kapozzz.domain.models.TomatoProgram
import com.kapozzz.tasks.screens.create_task_screen.CreateTaskState
import com.kapozzz.tasks.screens.create_task_screen.components.tomato_program.TomatoProgramsDialog
import com.kapozzz.ui.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskSettings(
    state: CreateTaskState,
    onDeleteTaskClick: () -> Unit,
    onDeleteTomatoProgramClick: (TomatoProgram) -> Unit,
    onProgramSave: (TomatoProgram) -> Unit,
    onProgramApply: (TomatoProgram) -> Unit,
    currentProgram: State<TomatoProgram>,
    modifier: Modifier = Modifier
) {
    val datePickerState = rememberDatePickerState()
    val datePickerIsVisible = remember {
        mutableStateOf(false)
    }
    val programsIsVisible = remember {
        mutableStateOf(false)
    }
    AnimatedVisibility(
        visible = state.settingsIsVisible.value,
        enter = expandVertically() + scaleIn(),
        exit = shrinkVertically() + scaleOut(),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(AppTheme.colors.container),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(AppTheme.colors.background),
                    onClick = { programsIsVisible.value = !programsIsVisible.value }
                ) {
                    Text(
                        text = "Programs",
                        style = AppTheme.typo.mediumBody,
                        color = AppTheme.colors.onBackground
                    )
                }
                if (programsIsVisible.value) TomatoProgramsDialog(
                    list = state.programs,
                    onDeleteClick = {
                        onDeleteTomatoProgramClick(it)
                    },
                    onDismissRequest = {
                        programsIsVisible.value = false
                    },
                    onProgramSave = onProgramSave,
                    onProgramApply = {
                        onProgramApply(it)
                        programsIsVisible.value = false
                    },
                    currentProgram = currentProgram
                )
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(AppTheme.colors.background),
                    onClick = { datePickerIsVisible.value = true }
                ) {
                    Text(
                        text = "Pick date",
                        style = AppTheme.typo.mediumBody,
                        color = AppTheme.colors.onBackground
                    )
                }
                if (datePickerIsVisible.value) {
                    DatePickerDialog(
                        onDismissRequest = { datePickerIsVisible.value = false },
                        confirmButton = {
                            TextButton(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(AppTheme.colors.background),
                                onClick = {
                                    datePickerIsVisible.value = false
                                    state.deadline.value = datePickerState.selectedDateMillis ?: 0
                                }
                            ) {
                                Text(
                                    text = "Pick",
                                    style = AppTheme.typo.mediumBody,
                                    color = AppTheme.colors.onBackground
                                )
                            }
                        }) {
                        DatePicker(state = datePickerState)
                    }
                }
                // DELETE BUTTON
                if (state.taskId.value != null) {
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(AppTheme.colors.background),
                        onClick = { onDeleteTaskClick() }
                    ) {
                        Text(
                            text = "Delete task",
                            style = AppTheme.typo.mediumBody,
                            color = AppTheme.colors.onBackground
                        )
                    }
                }
            }
        }
    }
}



