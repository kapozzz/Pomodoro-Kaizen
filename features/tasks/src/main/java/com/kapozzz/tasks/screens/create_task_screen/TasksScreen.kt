package com.kapozzz.tasks.screens.create_task_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kapozzz.presentation.components.AppButton
import com.kapozzz.tasks.R
import com.kapozzz.tasks.screens.create_task_screen.components.TasksTextField
import com.kapozzz.ui.AppTheme

@Composable
fun CreateTaskScreen(
    sendEvent: (event: CreateTaskEvent) -> Unit,
    state: CreateTaskState,
//    effects: SharedFlow<CreateTaskEffect>
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.background)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // NAME
            TasksTextField(
                text = state.name.value,
                onChange = { state.name.value = it },
                hint = stringResource(R.string.what_would_you_like_to_do),
                minLines = 2,
                maxLines = 2,
                maxSymbols = 50,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp
                    )
            )

            // ADD NEW STEP BUTTON
            AppButton(
                name = "Add new step" ,
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(top = 16.dp)
            )
            // STEPS


        }
    }
}

@Composable
@Preview
private fun ScreenPreview() {
    AppTheme {
        CreateTaskScreen(sendEvent = {}, state = CreateTaskState.getDefault())
    }
}

