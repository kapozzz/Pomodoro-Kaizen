package com.kapozzz.tasks.screens.create_task_screen.components.tomato_program

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kapozzz.domain.models.TomatoProgram
import com.kapozzz.presentation.components.AppButton
import com.kapozzz.tasks.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TomatoProgramsList(
    list: State<List<TomatoProgram>>,
    currentProgram: State<TomatoProgram>,
    onItemClick: (TomatoProgram) -> Unit,
    onDeleteClick: (TomatoProgram) -> Unit,
    onNewProgramClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(480.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = modifier.fillMaxHeight()
        ) {
            stickyHeader {
                TomatoProgramCard(
                    program = TomatoProgram.defaultProgram,
                    onItemClick = { onItemClick(TomatoProgram.defaultProgram) },
                    onDeleteClick = { },
                    deleteIsEnabled = false,
                    isPicked = currentProgram.value.id == TomatoProgram.defaultProgram.id
                )
            }
            items(
                items = list.value,
                key = { it.id }
            ) { program ->
                TomatoProgramCard(
                    program = program,
                    onItemClick = { onItemClick(program) },
                    onDeleteClick = { onDeleteClick(program) },
                    isPicked = currentProgram.value.id == program.id
                )
            }
            item {
                Spacer(
                    modifier = Modifier
                        .height(60.dp)
                )
            }
        }
        AppButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(8.dp),
            name = stringResource(R.string.new_program),
            onClick = { onNewProgramClick() }
        )
    }
}