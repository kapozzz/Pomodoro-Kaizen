package com.kapozzz.timer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kapozzz.ui.AppTheme

@Composable
internal fun TimerButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    TextButton(
        onClick = { onClick() },
        modifier = modifier
            .width(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = AppTheme.colors.outline,
                shape = RoundedCornerShape(16.dp)
            )
            .background(AppTheme.colors.primary)
    )
    {
        Text(
            text = text,
            style = AppTheme.typo.mediumBody,
            color = AppTheme.colors.onPrimary
        )
    }
}

@Composable
@Preview
private fun ButtonPreview() {
    AppTheme {
        TimerButton(text = "Start") {

        }
    }
}