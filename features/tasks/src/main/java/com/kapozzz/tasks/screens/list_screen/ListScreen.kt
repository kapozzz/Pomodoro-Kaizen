package com.kapozzz.tasks.screens.list_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.kapozzz.common.navigation.LocalAppNavigator
import com.kapozzz.presentation.root_components.AppUiComponents
import com.kapozzz.tasks.R
import com.kapozzz.tasks.screens.list_screen.components.ActualTasksScreen
import com.kapozzz.tasks.screens.list_screen.components.CompletedTasksScreen
import com.kapozzz.tasks.screens.list_screen.components.PagePicker
import com.kapozzz.ui.AppTheme
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ListScreen(
    state: ListScreenState,
    sendEvent: (event: ListScreenEvent) -> Unit,
    effect: SharedFlow<ListScreenEffect>
) {

    val navigator = LocalAppNavigator.current
    val lifecycle = LocalLifecycleOwner.current
    val uiComponents = AppUiComponents

    with(uiComponents) {
        topBarState.title.value = stringResource(R.string.tasks)
        topBarState.actions.value = {}
        topBarState.enabled.value = false
        topBarState.onBackClick.value = { navigator.back() }
        topBarState.onBackClickEnabled.value = false
        floatingButtonState.enabled.value = state.actualTasksList.value.isNotEmpty()
        floatingButtonState.content.value = {
            // CREATE NEW TASK BUTTON
            IconButton(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(AppTheme.colors.primary),
                onClick = { sendEvent(ListScreenEvent.CreateTask) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = AppTheme.colors.onPrimary,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            effect.collect { effect ->
                when (effect) {
                    is ListScreenEffect.CreateTask -> {
                        navigator.navigateToAddTask()
                    }

                    is ListScreenEffect.OnTaskLongTap -> {
                        navigator.navigateToAddTask(effect.id)
                    }

                    is ListScreenEffect.OnTaskTap -> {
                        navigator.navigateToTimer(effect.id)
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Start),
            text = "Pomodoro Kaizen!",
            style = AppTheme.typo.mediumTitleBold,
            color = AppTheme.colors.onBackground,
            textAlign = TextAlign.Start
        )
        val pagerState = rememberPagerState(pageCount = { 2 })
        PagePicker(pagerState = pagerState)
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState
        ) {
            when(it) {
                // ACTUAL TASKS
                0 -> {
                    ActualTasksScreen(
                        state = state,
                        sendEvent = sendEvent
                    )
                }
                // COMPLETED TASKS
                1 -> {
                    CompletedTasksScreen(
                        state = state,
                        sendEvent = sendEvent
                    )
                }
            }
        }
    }
}



