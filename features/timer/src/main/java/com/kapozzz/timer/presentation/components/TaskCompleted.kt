package com.kapozzz.timer.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kapozzz.presentation.components.AppButton
import com.kapozzz.presentation.root_components.AppUiComponents
import com.kapozzz.timer.R
import com.kapozzz.ui.AppTheme

@Composable
fun TaskCompleted(
    isCompleted: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    with(AppUiComponents) {
        topBarState.enabled.value = false
        floatingButtonState.enabled.value = false
    }

    AnimatedContent(
        targetState = isCompleted,
        label = stringResource(R.string.task_completed_animation)
    ) {
        if (it) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(AppTheme.colors.secondary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.task_completed),
                    style = AppTheme.typo.mediumTitle,
                    color = AppTheme.colors.onSecondary
                )
                AppButton(
                    name = "back to tasks screen",
                    onClick = { onBackClick() },
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                )
            }
        }
    }
}