package com.kapozzz.tasks.screens.create_task_screen.components.tomato_program

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kapozzz.domain.models.TomatoProgram
import com.kapozzz.presentation.components.AppButton
import com.kapozzz.tasks.R
import com.kapozzz.tasks.screens.create_task_screen.components.TasksTextField
import com.kapozzz.ui.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTomatoProgram(
    onProgramSave: (TomatoProgram) -> Unit,
    modifier: Modifier = Modifier,
) {
    val work = remember {
        SliderState(0f)
    }
    val rest = remember {
        SliderState(0f)
    }
    val long = remember {
        SliderState(0f)
    }
    val name = remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TasksTextField(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            text = name.value,
            onChange = {
                name.value = it
            },
            hint = "Program name"
        )
        TimeSlider(
            state = work,
            name = stringResource(R.string.work_time_create)
        )
        TimeSlider(
            state = rest,
            name = stringResource(R.string.rest_time_create)
        )
        TimeSlider(
            state = long,
            name = stringResource(R.string.long_time_create)
        )
        AppButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            name = stringResource(R.string.save_in_create),
            onClick = {
                val newProgram = TomatoProgram(
                    name = name.value,
                    workTime = work.value.toInt(),
                    restTime = rest.value.toInt(),
                    longRestTime = long.value.toInt()
                )
                onProgramSave(newProgram)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimeSlider(
    state: SliderState,
    name: String
) {

    val sliderValue = remember { mutableFloatStateOf(state.value) }

    Column(
        modifier = Modifier
            .background(AppTheme.colors.container)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = AppTheme.typo.smallBody,
                color = AppTheme.colors.onContainer
            )
            Text(
                text = sliderValue.floatValue.toInt().toString(),
                style = AppTheme.typo.smallBody,
                color = AppTheme.colors.onContainer
            )
        }
        Slider(
            value = sliderValue.floatValue,
            onValueChange = { newValue ->
                sliderValue.floatValue = newValue
                state.value = newValue
            },
            valueRange = 0f..60f,
            modifier = Modifier
                .fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = AppTheme.colors.primary,
                activeTrackColor = AppTheme.colors.primary,
                inactiveTrackColor = AppTheme.colors.outline
            )
        )
    }
}
