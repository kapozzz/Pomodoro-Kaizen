package com.kapozzz.timer.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.kapozzz.ui.AppTheme
import kotlinx.coroutines.delay


@Composable
internal fun TimerCircularProgressBar(
    percentage: Float,
    number: Int,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 22.sp,
    radius: Dp = 50.dp,
    strokeWidth: Dp = 8.dp,
    animationDuration: Int = 1000,
    animDelay: Int = 0
) {
    val primaryColor = AppTheme.colors.primary
    val containerColor = AppTheme.colors.primaryContainer
    val animatedPercentage = remember { Animatable(0f) }
    LaunchedEffect(percentage) {
        animatedPercentage.animateTo(
            targetValue = percentage,
            animationSpec = tween(durationMillis = animationDuration, delayMillis = animDelay)
        )
    }
    Box(
        modifier = modifier
            .padding(4.dp)
            .size(2f * radius),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(2f * radius)
        ) {
            drawArc(
                color = containerColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = primaryColor,
                startAngle = -90f,
                sweepAngle = 360 * animatedPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        AnimatedContent(
            targetState = number
        ) {
            Text(
                text = it.toString(),
                color = AppTheme.colors.onBackground,
                style = AppTheme.typo.mediumBody
            )
        }
    }
}

@Composable
@Preview
private fun Prev() {
    AppTheme {
        val per = remember {
            mutableStateOf(1f)
        }
        TimerCircularProgressBar(percentage = per.value, number = per.value.toInt())
        val percents = generateSequence(1.0) { it - 0.01 }
            .takeWhile { it >= 0.1 }
            .toList()
        LaunchedEffect(key1 = true) {
            for (percent in percents) {
                delay(1000L)
                per.value = percent.toFloat()
            }
        }
    }
}