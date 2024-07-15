package com.kapozzz.timer.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.kapozzz.common.navigation.LocalAppNavigator
import com.kapozzz.presentation.root_components.AppUiComponents
import com.kapozzz.timer.R
import com.kapozzz.timer.presentation.components.TaskCompleted
import com.kapozzz.timer.presentation.components.TimerCircularProgressBar
import com.kapozzz.timer.presentation.components.TimerDescription
import com.kapozzz.timer.presentation.components.TimerStage
import com.kapozzz.ui.AppTheme
import kotlinx.coroutines.flow.SharedFlow

@Composable
internal fun TimerScreen(
    state: TimerState,
    sendEvent: (event: TimerEvent) -> Unit,
    effects: SharedFlow<TimerEffect>
) {

    val context = LocalContext.current
    val navigator = LocalAppNavigator.current

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                sendEvent(TimerEvent.SwitchNotifications(true))
            } else {
                sendEvent(TimerEvent.SwitchNotifications(false))
            }
        }


    val lifecycle = LocalLifecycleOwner.current

    LaunchedEffect(key1 = true) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            effects.collect {
                when (it) {
                    is TimerEffect.TimeExpires -> TODO()
                    is TimerEffect.TimerIsStarted -> {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_DENIED
                        ) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            }
                        }
                    }

                    is TimerEffect.OnBackClick -> {
                        navigator.back()
                    }
                }
            }
        }
    }

    with(AppUiComponents) {
        topBarState.enabled.value = true
        topBarState.title.value = state.task.value.name
        topBarState.onBackClick.value = { navigator.back() }
        floatingButtonState.enabled.value = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TimerStage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp),
                tomato = state.tomato.value,
                step = state.step.value,
                stepNumber = state.stepIndex.value
            )
            // DESCRIPTION
            TimerDescription(
                modifier = Modifier.padding(top = 4.dp),
                description = state.step.value.description
            )

        }
        //TIMER
        Timer(
            modifier = Modifier
                .align(Alignment.Center)
                .height(500.dp),
            state = state,
            sendEvent = sendEvent,
        )
        // BACK BUTTON
        IconButton(
            onClick = { sendEvent(TimerEvent.OnBackClick) },
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(60.dp)
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = AppTheme.colors.onBackground
            )
        }
        // TASK COMPLETED ANIMATED VIEW
        TaskCompleted(
            isCompleted = state.isCompleted.value,
            onBackClick = { navigator.back() },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Timer(
    state: TimerState,
    sendEvent: (event: TimerEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimerCircularProgressBar(
            modifier = Modifier.padding(bottom = 16.dp),
            percentage = state.percentage.value,
            minutes = state.minutes.value,
            seconds = state.seconds.value
        )
        AnimatedContent(
            targetState = state.isWorking.value, label = "",
        ) {
            Icon(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .size(60.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .combinedClickable(
                        onClick = { sendEvent(TimerEvent.SwitchTimer) },
                        onLongClick = { sendEvent(TimerEvent.ResetTimer) }
                    ),
                painter = painterResource(if (it) R.drawable.pause_icon else R.drawable.play_icon),
                contentDescription = null,
                tint = AppTheme.colors.primary
            )
        }
        AnimatedVisibility(visible = state.minutes.value > 0 || state.seconds.value > 0) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Hold to reset",
                color = AppTheme.colors.hint,
                style = AppTheme.typo.smallBody
            )
        }
    }
}

