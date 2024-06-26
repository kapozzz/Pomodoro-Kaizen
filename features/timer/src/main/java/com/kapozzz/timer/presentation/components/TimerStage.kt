package com.kapozzz.timer.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kapozzz.domain.models.Step
import com.kapozzz.domain.models.Tomato
import com.kapozzz.ui.AppTheme

@Composable
internal fun TimerStage(
    tomato: Tomato,
    step: Step,
    stepNumber: Int,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = tomato,
        modifier = modifier,
        label = ""
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = if (it == Tomato.Work) "${stepNumber + 1}. ${step.title}" else "Time to relax!",
                color = AppTheme.colors.onBackground,
                style = AppTheme.typo.mediumTitleBold,
                textAlign = TextAlign.Start
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(AppTheme.colors.outline)
            )
        }
    }
}