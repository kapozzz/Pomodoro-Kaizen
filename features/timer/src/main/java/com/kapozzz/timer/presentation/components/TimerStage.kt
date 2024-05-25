package com.kapozzz.timer.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kapozzz.timer.presentation.model.PomodoroStage
import com.kapozzz.ui.AppTheme

@Composable
internal fun TimerStage(
    stage: PomodoroStage,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = stage,
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = if (it == PomodoroStage.WorkPomodoro) "Pomodoro Kaizen!" else "Time to relax!",
            color = AppTheme.colors.onBackground,
            style = AppTheme.typo.mediumTitle
        )
    }
}