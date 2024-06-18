package com.kapozzz.tasks.screens.list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.kapozzz.common.navigation.LocalAppNavigator
import com.kapozzz.domain.models.Task
import com.kapozzz.domain.models.TomatoProgram
import com.kapozzz.presentation.root_components.AppUiComponents
import com.kapozzz.tasks.screens.list_screen.components.TaskCard
import com.kapozzz.ui.AppTheme
import kotlinx.coroutines.flow.SharedFlow

@Composable
internal fun ListScreen(
    state: ListScreenState,
    sendEvent: (event: ListScreenEvent) -> Unit,
    effect: SharedFlow<ListScreenEffect>
) {

    val navigator = LocalAppNavigator.current
    val lifecycle = LocalLifecycleOwner.current

    with(AppUiComponents) {
        topBarState.title.value = "Tasks"
        topBarState.actions.value = {}
        topBarState.enabled.value = true
        topBarState.onBackClick.value = { navigator.back() }
        floatingButtonState.enabled.value = true
        floatingButtonState.content.value = {
            // CREATE NEW TASK BUTTON
            IconButton(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .background(AppTheme.colors.primary),
                onClick = { sendEvent(ListScreenEvent.CreateTask) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = AppTheme.colors.onPrimary
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            effect.collect { effect ->
                when(effect) {
                    is ListScreenEffect.CreateTask -> {
                        navigator.navigateToAddTask()
                    }
                    is ListScreenEffect.OnTaskLongTap -> {
                        navigator.navigateToAddTask(effect.id)
                    }
                    is ListScreenEffect.OnTaskTap -> {

                    }
                }
            }
        }
    }

    Screen(
        state = state,
        sendEvent = sendEvent
    )
}

@Composable
private fun Screen(state: ListScreenState, sendEvent: (event: ListScreenEvent) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // TASKS LIST
        items(state.list.value) {
            TaskCard(
                task = it,
                onLongTap = { sendEvent(ListScreenEvent.OnTaskLongTap(it.id)) },
                onTap = { sendEvent(ListScreenEvent.OnTaskTap(it.id)) }
            )
        }
    }
}

@Preview
@Composable
private fun TasksScreenPreview() {

    val state = ListScreenState.getDefault()
    state.list.value = listOf(
        Task(
            id = "1",
            name = "First task",
            steps = emptyList(),
            program = TomatoProgram.defaultPrograms.first(),
            deadline = 0
        ),
        Task(
            id = "2",
            name = "Second task",
            steps = emptyList(),
            program = TomatoProgram.defaultPrograms.first(),
            deadline = 0
        ),
        Task(
            id = "3",
            name = "Third task",
            steps = emptyList(),
            program = TomatoProgram.defaultPrograms.first(),
            deadline = 0
        )
    )

    AppTheme {
        Screen(state = state, sendEvent = {})
    }

}


