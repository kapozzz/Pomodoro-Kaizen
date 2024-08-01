package com.kapozzz.tasks.screens.create_task_screen.components.tomato_program

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kapozzz.domain.models.TomatoProgram
import com.kapozzz.tasks.R
import com.kapozzz.ui.AppTheme

@Composable
fun TomatoProgramCard(
    program: TomatoProgram,
    isPicked: Boolean,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    deleteIsEnabled: Boolean = true,
) {
    Box(
        modifier = modifier
            .height(90.dp)
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isPicked) AppTheme.colors.secondary else AppTheme.colors.outline)
            .clickable {
                onItemClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = program.name,
                    style = AppTheme.typo.smallTitle,
                    color = AppTheme.colors.onContainer,
                    textAlign = TextAlign.Start
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = com.kapozzz.presentation.R.drawable.time_icon),
                        contentDescription = null,
                        tint = AppTheme.colors.onContainer
                    )
                    Text(
                        text = stringResource(R.string.work_time) + program.workTime,
                        style = AppTheme.typo.smallBody,
                        color = AppTheme.colors.onContainer,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = stringResource(R.string.rest_time) + program.restTime,
                        style = AppTheme.typo.smallBody,
                        color = AppTheme.colors.onContainer,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = stringResource(R.string.long_rest_time) + program.longRestTime,
                        style = AppTheme.typo.smallBody,
                        color = AppTheme.colors.onContainer,
                        textAlign = TextAlign.Start
                    )
                }
            }
            IconButton(onClick = { onDeleteClick() }) {
                if (deleteIsEnabled) Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = AppTheme.colors.onContainer
                )
            }
        }
    }
}