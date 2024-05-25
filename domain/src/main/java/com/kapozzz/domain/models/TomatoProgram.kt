package com.kapozzz.domain.models

sealed class TomatoProgram(
    val workTime: Int,
    val restTime: Int,
    val longRestTime: Int
) {
    data object DefaultProgram: TomatoProgram(
        workTime = 25,
        restTime = 5,
        longRestTime = 30
    )
    data object FastProgram: TomatoProgram(
        workTime = 10,
        restTime = 1,
        longRestTime = 5
    )
}