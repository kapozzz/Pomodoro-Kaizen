package com.kapozzz.timer.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kapozzz.timer.R
import com.kapozzz.timer.presentation.components.TimerCircularProgressBar
import com.kapozzz.ui.AppTheme
import kotlinx.coroutines.flow.SharedFlow

@Composable
internal fun TimerScreen(
    state: TimerState,
    sendEvent: (event: TimerEvent) -> Unit,
    effect: SharedFlow<TimerEffect>
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Pomodoro Kaizen!",
                color = AppTheme.colors.onBackground,
                style = AppTheme.typo.mediumTitle
            )
            Timer(
                state = state,
                sendEvent = sendEvent,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

    }


}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
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
            targetState = state.isWorking.value,
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
                painter = painterResource( if (it) R.drawable.pause_icon else R.drawable.play_icon),
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

