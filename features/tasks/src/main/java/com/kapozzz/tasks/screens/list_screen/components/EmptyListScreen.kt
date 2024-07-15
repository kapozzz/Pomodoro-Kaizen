package com.kapozzz.tasks.screens.list_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kapozzz.presentation.components.AppButton
import com.kapozzz.tasks.R
import com.kapozzz.ui.AppTheme

@Composable
fun EmptyListScreen(
    onCreateTaskClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.width(IntrinsicSize.Max),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = stringResource(R.string.you_have_no_tasks),
                color = AppTheme.colors.onBackground,
                style = AppTheme.typo.largeBody
            )
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                name = stringResource(R.string.create_first_task),
                onClick = onCreateTaskClick,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = null,
                        tint = AppTheme.colors.onPrimary
                    )
                }
            )
        }
    }
}
