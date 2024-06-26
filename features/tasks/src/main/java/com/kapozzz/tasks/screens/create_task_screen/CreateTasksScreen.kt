package com.kapozzz.tasks.screens.create_task_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.kapozzz.common.extentions.toFormattedDateString
import com.kapozzz.common.navigation.AppNavigator
import com.kapozzz.presentation.components.AppButton
import com.kapozzz.presentation.root_components.AppUiComponents
import com.kapozzz.tasks.R
import com.kapozzz.tasks.screens.create_task_screen.components.AddNewTaskBottomSheet
import com.kapozzz.tasks.screens.create_task_screen.components.StepCard
import com.kapozzz.tasks.screens.create_task_screen.components.TaskSettings
import com.kapozzz.tasks.screens.create_task_screen.components.TasksTextField
import com.kapozzz.ui.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CreateTaskScreen(
    sendEvent: (event: CreateTaskEvent) -> Unit,
    state: CreateTaskState,
    effects: SharedFlow<CreateTaskEffect>
) {
    with(AppUiComponents) {
        topBarState.also {
            it.title.value = "New task"
            it.enabled.value = true
            it.onBackClick.value = { sendEvent(CreateTaskEvent.Back) }
            it.onBackClickEnabled.value = true
            it.actions.value = {
                IconButton(onClick = {
                    state.settingsIsVisible.value = !state.settingsIsVisible.value
                }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null
                    )
                }
            }
        }
        floatingButtonState.also {
            it.enabled.value = true
            it.content.value = {
                TextButton(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(AppTheme.colors.primary),
                    onClick = { sendEvent(CreateTaskEvent.SaveTask) }
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(R.string.save_button),
                        style = AppTheme.typo.largeBody,
                        color = AppTheme.colors.onPrimary
                    )
                }
            }
        }
    }
    val lifecycle = LocalLifecycleOwner.current
    val appNavigator = AppUiComponents.appNavigator
    val snackBarState = AppUiComponents.snackBarState
    val scope = rememberCoroutineScope()
    LaunchedEffect(true) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            effects.collect { effect ->
                handleEffect(effect, appNavigator, snackBarState, scope)
            }
        }
    }
    Screen(
        state = state,
        sendEvent = sendEvent
    )
}

fun handleEffect(
    effect: CreateTaskEffect,
    appNavigator: AppNavigator,
    snackBarState: SnackbarHostState,
    scope: CoroutineScope
) {
    when (effect) {
        is CreateTaskEffect.Back -> {
            appNavigator.back()
        }
        is CreateTaskEffect.SaveComplete -> {
            scope.launch {
                withContext(Dispatchers.Main) {
                    snackBarState.showSnackbar("Task created!")
                }
            }
        }
        is CreateTaskEffect.ShowMessage -> {
            val message = when(effect.type) {
                CreateTaskMessage.EmptyName -> {
                    "Empty name"
                }
                CreateTaskMessage.EmptySteps -> {
                    "Empty steps"
                }
                CreateTaskMessage.UnknownError -> {
                    "Failed to create a task"
                }
            }
            scope.launch {
                withContext(Dispatchers.Main) {
                    snackBarState.showSnackbar(message)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    state: CreateTaskState,
    sendEvent: (event: CreateTaskEvent) -> Unit
) {

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.background)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // SETTINGS
            item {
                TaskSettings(
                    program = state.program.value,
                    newProgram = {
                        state.program.value = it
                        Log.i("PROGRAM", "NEW: $it")
                    },
                    deadline = state.deadline.value,
                    newDeadline = {
                        state.deadline.value = it
                    },
                    isVisible = state.settingsIsVisible.value
                )
            }
            // NAME
            item {
                TasksTextField(
                    text = state.name.value,
                    onChange = { state.name.value = it },
                    hint = stringResource(R.string.what_would_you_like_to_do),
                    minLines = 2,
                    maxLines = 2,
                    maxSymbols = 50,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 16.dp
                        )
                )
            }
            // DEADLINE
            item {
                if (state.deadline.value != 0L) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(AppTheme.colors.container),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Make it to",
                                style = AppTheme.typo.mediumBody,
                                color = AppTheme.colors.onBackground
                            )
                            Text(
                                text = state.deadline.value.toFormattedDateString(),
                                style = AppTheme.typo.mediumBody,
                                color = AppTheme.colors.onBackground
                            )
                        }
                    }
                }
            }
            // STEPS
            itemsIndexed(
                state.steps.value,
                key = { index, _ -> index },
            ) { index, step ->
                StepCard(
                    modifier = Modifier.padding(top = 16.dp),
                    step = step,
                    onClick = { sendEvent(CreateTaskEvent.ChangeStep(index)) },
                    onDeleteClick = { sendEvent(CreateTaskEvent.DeleteStep(index)) },
                    index = index + 1
                )
            }
            // ADD NEW STEP BUTTON
            item {
                AppButton(
                    name = stringResource(R.string.add_new_step),
                    onClick = {
                        sendEvent(CreateTaskEvent.AddNewStep)
                        state.bottomSheetIsVisible.value = true
                    },
                    modifier = Modifier.padding(top = 16.dp),
                    image = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = AppTheme.colors.onPrimary
                        )
                    }
                )
            }
        }
        // BOTTOM SHEET
        if (state.bottomSheetIsVisible.value) {
            AddNewTaskBottomSheet(
                sheetState = bottomSheetState,
                state = state,
                onSave = {
                    sendEvent(CreateTaskEvent.SaveStep(false))
                    state.bottomSheetIsVisible.value = false
                },
                onDismiss = {
                    sendEvent(CreateTaskEvent.SaveStep(true))
                    state.bottomSheetIsVisible.value = false
                }
            )
        }
    }
}

