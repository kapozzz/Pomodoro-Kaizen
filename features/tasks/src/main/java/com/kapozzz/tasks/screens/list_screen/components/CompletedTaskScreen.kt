package com.kapozzz.tasks.screens.list_screen.components

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kapozzz.presentation.components.LocalAppDialog
import com.kapozzz.tasks.R
import com.kapozzz.tasks.screens.list_screen.ListScreenEvent
import com.kapozzz.tasks.screens.list_screen.ListScreenState
import com.kapozzz.ui.AppTheme

@Composable
internal fun CompletedTasksScreen(
    state: ListScreenState,
    sendEvent: (event: ListScreenEvent) -> Unit
) {
    val lazyColumnScrollState = rememberLazyListState()
    AnimatedContent(targetState = state.completedTasksList.value.isEmpty(), label = "") {
        if (it) {
            Text(
                text = stringResource(R.string.you_do_not_yet_have_any_completed_tasks),
                color = AppTheme.colors.onBackground,
                style = AppTheme.typo.largeBody
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
            state.completedTasksList.value,
            key = {
                it.id
            }
        ) {
            CompletedTaskCard(
                task = it,
                onDelete = {
                    appDialogState.isVisible.value = true
                    appDialogState.apply {
                        onAccess.value = { sendEvent(ListScreenEvent.DeleteTask(it.id)) }
                        message.value = deleteMessageForAppDialog
                    }
                },
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