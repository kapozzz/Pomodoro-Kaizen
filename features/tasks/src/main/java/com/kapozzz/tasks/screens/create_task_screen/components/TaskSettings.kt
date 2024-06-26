package com.kapozzz.tasks.screens.create_task_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.kapozzz.domain.models.TomatoProgram
import com.kapozzz.ui.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskSettings(
    program: TomatoProgram,
    newProgram: (TomatoProgram) -> Unit,
    deadline: Long,
    newDeadline: (Long) -> Unit,
    isVisible: Boolean,
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
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
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
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .background(AppTheme.colors.background),
                    onClick = { programsIsVisible.value = !programsIsVisible.value }
                ) {
                    Text(
                        text = "Programs",
                        style = AppTheme.typo.mediumBody,
                        color = AppTheme.colors.onBackground
                    )
                }
                DropdownMenu(
                    modifier = Modifier
                        .background(AppTheme.colors.container),
                    expanded = programsIsVisible.value,
                    onDismissRequest = {
                        programsIsVisible.value = false
                    },
                    offset = DpOffset(150.dp, 0.dp)
                ) {
                    DropdownMenuItem(text = {
                        Text(
                            text = "Default",
                            style = AppTheme.typo.smallBody,
                            color = AppTheme.colors.onBackground
                        )
                    }, onClick = {
                        newProgram(TomatoProgram.defaultPrograms.first())
                        programsIsVisible.value = false
                    })
                    DropdownMenuItem(text = {
                        Text(
                            text = "Fast",
                            style = AppTheme.typo.smallBody,
                            color = AppTheme.colors.onBackground
                        )
                    }, onClick = {
                        newProgram(TomatoProgram.defaultPrograms.last())
                        programsIsVisible.value = false
                    })
                }
                TextButton(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .padding(top = 8.dp)
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
                                    newDeadline(datePickerState.selectedDateMillis ?: 0)
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
            }
        }
    }
}



