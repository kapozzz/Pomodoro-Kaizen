package com.kapozzz.tasks.screens.create_task_screen.components


import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kapozzz.presentation.components.AppButton
import com.kapozzz.presentation.other.rememberKeyboardState
import com.kapozzz.tasks.R
import com.kapozzz.tasks.screens.create_task_screen.CreateTaskState
import com.kapozzz.ui.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewTaskBottomSheet(
    state: CreateTaskState,
    onSave: () -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState
) {

    val error = remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.End),
                    onClick = {
                        state.bottomSheetIsVisible.value = false
                        onDismiss()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
                TasksTextField(
                    text = state.currentStepTitle.value,
                    onChange = {
                        state.currentStepTitle.value = it
                        if (it.isNotEmpty()) error.value = false
                    },
                    hint = stringResource(R.string.name_of_the_new_task),
                    minLines = 3,
                    maxLines = 4,
                    maxSymbols = 50
                )
                AnimatedVisibility(
                    visible = error.value
                ) {
                    Text(
                        text = stringResource(R.string.the_title_can_t_be_empty),
                        style = AppTheme.typo.smallBody,
                        color = AppTheme.colors.error,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp),
                        textAlign = TextAlign.Start
                    )
                }
                TasksTextField(
                    modifier = Modifier.padding(top = 16.dp),
                    text = state.currentStepDescription.value,
                    onChange = { state.currentStepDescription.value = it },
                    hint = "Description",
                    minLines = 5,
                    maxLines = 16,
                    maxSymbols = 300
                )
                AppButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    name = stringResource(R.string.save),
                    onClick = {
                        if (state.currentStepTitle.value.isNotEmpty()) {
                            onSave()
                        } else {
                            error.value = true
                        }
                    }
                )
            }
        }
    }
}
