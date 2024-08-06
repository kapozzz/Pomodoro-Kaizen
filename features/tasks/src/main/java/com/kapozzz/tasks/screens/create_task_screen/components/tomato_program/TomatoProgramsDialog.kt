package com.kapozzz.tasks.screens.create_task_screen.components.tomato_program

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kapozzz.domain.models.TomatoProgram
import com.kapozzz.ui.AppTheme

@Composable
fun TomatoProgramsDialog(
    list: State<List<TomatoProgram>>,
    currentProgram: State<TomatoProgram>,
    onDeleteClick: (TomatoProgram) -> Unit,
    onDismissRequest: () -> Unit,
    onProgramSave: (TomatoProgram) -> Unit,
    onProgramApply: (TomatoProgram) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentScreen = remember {
        mutableStateOf(TomatoProgramsDialogScreen.List)
    }
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = modifier
                .width(320.dp),
            colors = CardDefaults.cardColors(
                containerColor = AppTheme.colors.background
            )
        ) {
            AnimatedContent(targetState = currentScreen.value, label = "") {
                when (it) {
                    TomatoProgramsDialogScreen.List -> {
                        TomatoProgramsList(
                            list = list,
                            onItemClick = { program ->
                                onProgramApply(program)
                            },
                            onDeleteClick = onDeleteClick,
                            onNewProgramClick = {
                                currentScreen.value = TomatoProgramsDialogScreen.Create
                            },
                            currentProgram = currentProgram
                        )
                    }
                    TomatoProgramsDialogScreen.Create -> {
                        CreateTomatoProgram(
                            onProgramSave = {
                                onProgramSave(it)
                                currentScreen.value = TomatoProgramsDialogScreen.List
                            },
                            onBackClick = {
                                currentScreen.value = TomatoProgramsDialogScreen.List
                            }
                        )
                    }
                }
            }
        }
    }
}

private enum class TomatoProgramsDialogScreen {
    List, Create
}