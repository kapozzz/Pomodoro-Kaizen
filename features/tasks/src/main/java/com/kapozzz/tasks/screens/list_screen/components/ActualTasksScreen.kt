package com.kapozzz.tasks.screens.list_screen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kapozzz.presentation.components.LocalAppDialog
import com.kapozzz.tasks.R
import com.kapozzz.tasks.screens.list_screen.ListScreenEvent
import com.kapozzz.tasks.screens.list_screen.ListScreenState

@Composable
internal fun ActualTasksScreen(
    state: ListScreenState,
    sendEvent: (event: ListScreenEvent) -> Unit
) {
    val lazyColumnScrollState = rememberLazyListState()
    AnimatedContent(targetState = state.actualTasksList.value.isEmpty(), label = "") {
        if (it) {
            EmptyListScreen(
                onCreateTaskClick = { sendEvent(ListScreenEvent.CreateTask) }
            )
        } else {
            Screen(
                state = state,
                sendEvent = sendEvent,
                lazyListState = lazyColumnScrollState
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Screen(
    state: ListScreenState,
    sendEvent: (event: ListScreenEvent) -> Unit,
    lazyListState: LazyListState
) {
    val appDialogState = LocalAppDialog.current
    val deleteMessageForAppDialog = stringResource(R.string.do_you_want_to_delete_the_task)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = lazyListState
    ) {
        // TASKS LIST
        items(
            state.actualTasksList.value,
            key = {
                it.id
            }
        ) {
            ActualTaskCard(
                task = it,
                onTap = { sendEvent(ListScreenEvent.OnTaskTap(it.id)) },
                onDelete = {
                    appDialogState.isVisible.value = true
                    appDialogState.apply {
                        onAccess.value = { sendEvent(ListScreenEvent.DeleteTask(it.id)) }
                        message.value = deleteMessageForAppDialog
                    }
                },
                onEdit = { sendEvent(ListScreenEvent.OnTaskLongTap(it.id)) },
                modifier = Modifier.animateItemPlacement()
            )
        }
        // SPACER
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
        }
    }
}