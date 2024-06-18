package com.kapozzz.tasks.screens.list_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kapozzz.domain.models.Task
import com.kapozzz.ui.AppTheme
import kotlinx.coroutines.launch

@Composable
internal fun TaskCard(
    task: Task,
    onTap: () -> Unit,
    onLongTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    val millis = 2000L
    val tapped = remember { mutableStateOf(false) }
    val animationProgress = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(76.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(AppTheme.colors.container)
            .border(
                width = 1.dp,
                color = AppTheme.colors.primary,
                shape = RoundedCornerShape(28.dp)
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        tapped.value = true
                        scope.launch {
                            animationProgress.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(durationMillis = millis.toInt())
                            )
                            if (animationProgress.value == 1f) {
                                onLongTap()
                                tapped.value = false
                                scope.launch {
                                    animationProgress.stop()
                                    animationProgress.snapTo(0f)
                                }
                            }
                        }
                    },
                    onPress = {
                        val startTime = System.currentTimeMillis()
                        tryAwaitRelease()
                        val timeLater = System.currentTimeMillis() - startTime
                        if (timeLater < millis) {
                            tapped.value = false
                            scope.launch {
                                animationProgress.stop()
                                animationProgress.snapTo(0f)
                            }
                        }
                    },
                    onTap = {
                        onTap()
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = task.name,
                style = AppTheme.typo.smallTitle,
                color = AppTheme.colors.onContainer,
                textAlign = TextAlign.Start,
                maxLines = 1
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(visible = tapped.value) {
                    Text(
                        text = "Edit",
                        style = AppTheme.typo.largeBody,
                        color = AppTheme.colors.onContainer
                    )
                }
                val circleColor = AppTheme.colors.secondary
                Canvas(
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                ) {
                    val sweepAngle = 360 * animationProgress.value
                    drawArc(
                        color = circleColor,
                        startAngle = -90f,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        size = Size(size.width, size.height)
                    )
                }
            }
        }
    }
}
