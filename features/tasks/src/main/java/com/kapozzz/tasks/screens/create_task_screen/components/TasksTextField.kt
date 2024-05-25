package com.kapozzz.tasks.screens.create_task_screen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.kapozzz.ui.AppTheme

@Composable
fun TasksTextField(
    text: String,
    onChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    maxSymbols: Int = 30,
    minLines: Int = 3,
    maxLines: Int = 9
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(16.dp))
//            .border(
//                width = 1.dp,
//                color = AppTheme.colors.containerOutline,
//                shape = RoundedCornerShape(16.dp)
//            )
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .background(AppTheme.colors.container),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = text,
            onValueChange = { if (it.length <= maxSymbols) onChange(it) },
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            textStyle = AppTheme.typo.mediumBody,
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = AppTheme.colors.onContainer,
                focusedTextColor = AppTheme.colors.onContainer,
                unfocusedContainerColor = AppTheme.colors.container,
                focusedContainerColor = AppTheme.colors.container,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = hint,
                    style = AppTheme.typo.smallBody,
                    color = AppTheme.colors.hint
                )
            },
            trailingIcon = {
                if (text.isNotEmpty()) {
                    IconButton(onClick = { onChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                    }
                }
            },
            enabled = enabled,
            minLines = minLines,
            maxLines = maxLines
        )
        if (enabled) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val color =
                    if (text.length < maxSymbols) AppTheme.colors.onContainer else AppTheme.colors.error
                Text(
                    text = "${text.length} / ",
                    style = AppTheme.typo.smallBody,
                    color = color
                )
                Text(
                    text = maxSymbols.toString(),
                    style = AppTheme.typo.smallBody,
                    color = color
                )
            }
        }
    }
}

@Composable
@Preview
private fun TextFieldPreview() {
    AppTheme {
        val text = remember {
            mutableStateOf("Wow!")
        }
        TasksTextField(
            text = text.value,
            onChange = { text.value = it },
            enabled = true,
            hint = "Put your new task here!"
        )
    }
}