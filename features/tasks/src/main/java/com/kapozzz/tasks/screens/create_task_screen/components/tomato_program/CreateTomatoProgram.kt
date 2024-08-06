package com.kapozzz.tasks.screens.create_task_screen.components.tomato_program

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kapozzz.domain.models.TomatoProgram
import com.kapozzz.presentation.components.AppButton
import com.kapozzz.tasks.R
import com.kapozzz.tasks.screens.create_task_screen.components.TasksTextField
import com.kapozzz.ui.AppTheme

private const val TIME_FROM = 0
private const val TIME_TO = 60

private data class TomatoProgramTimerState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val work: SliderState = SliderState(
        value = 0f,
        valueRange = TIME_FROM.toFloat()..TIME_TO.toFloat()
    ),
    val rest: SliderState = SliderState(
        value = 0f,
        valueRange = TIME_FROM.toFloat()..TIME_TO.toFloat()
    ),
    val long: SliderState = SliderState(
        value = 0f,
        valueRange = TIME_FROM.toFloat()..TIME_TO.toFloat()
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTomatoProgram(
    onProgramSave: (TomatoProgram) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sliderState = remember {
        TomatoProgramTimerState()
    }
    val name = remember {
        mutableStateOf("")
    }
    val errorInName = remember {
        mutableStateOf(false)
    }
    val errorInTimer = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .background(AppTheme.colors.background),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }
        TasksTextField(
            text = name.value,
            onChange = {
                name.value = it
            },
            hint = stringResource(R.string.program_name)
        )
        AnimatedVisibility(
            visible = errorInName.value
        ) {
            Text(
                text = stringResource(R.string.name_can_t_be_empty),
                style = AppTheme.typo.smallBody,
                color = AppTheme.colors.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                textAlign = TextAlign.Start
            )
        }
        TimeSlider(
            state = sliderState.work,
            name = stringResource(R.string.work_time_create)
        )
        TimeSlider(
            state = sliderState.rest,
            name = stringResource(R.string.rest_time_create)
        )
        TimeSlider(
            state = sliderState.long,
            name = stringResource(R.string.long_time_create)
        )
        AnimatedVisibility(
            visible = errorInTimer.value
        ) {
            Text(
                text = "Time can`b equals 0",
                style = AppTheme.typo.smallBody,
                color = AppTheme.colors.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                textAlign = TextAlign.Start
            )
        }
        AppButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            name = stringResource(R.string.save_in_create),
            onClick = {
                when {
                    name.value.isEmpty() -> errorInName.value = true
                    sliderState.work.value == 0f || sliderState.rest.value == 0f ||
                            sliderState.long.value == 0f -> errorInTimer.value = true
                    else -> {
                        with(sliderState) {
                            val newProgram = TomatoProgram(
                                name = name.value,
                                workTime = work.value.toInt(),
                                restTime = rest.value.toInt(),
                                longRestTime = long.value.toInt()
                            )
                            onProgramSave(newProgram)
                        }
                    }
                }
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
            .clip(RoundedCornerShape(8.dp))
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
                text = state.value.toInt().toString(),
                style = AppTheme.typo.smallBody,
                color = AppTheme.colors.onContainer
            )
        }
        Slider(
            value = sliderValue.floatValue,
            onValueChange = { newValue ->
                sliderValue.floatValue = newValue.toInt().toFloat()
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

@Preview
@Composable
private fun TomatoProgramCreatePreview() {
    AppTheme {
        CreateTomatoProgram(
            onProgramSave = {},
            onBackClick = {}
        )
    }
}

