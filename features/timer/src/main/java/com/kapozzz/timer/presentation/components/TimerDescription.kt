package com.kapozzz.timer.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kapozzz.ui.AppTheme

@Composable
internal fun TimerDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        if (description.length > 100) {
            val expanded = remember {
                mutableStateOf(false)
            }
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    text = if (expanded.value) description else "${description.take(100)} ...",
                    style = AppTheme.typo.mediumBody,
                    color = AppTheme.colors.onBackground
                )
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable {
                            expanded.value = !expanded.value
                        },
                    text = if (expanded.value) stringResource(com.kapozzz.timer.R.string.hide)
                    else stringResource(com.kapozzz.timer.R.string.more),
                    style = AppTheme.typo.mediumBody,
                    color = AppTheme.colors.outline
                )
            }
        } else {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = description,
                style = AppTheme.typo.mediumBody,
                color = AppTheme.colors.onBackground
            )
        }
    }
}