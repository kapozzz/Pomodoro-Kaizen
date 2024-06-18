package com.kapozzz.domain.models

data class TomatoProgram(
    val name: String,
    val workTime: Int,
    val restTime: Int,
    val longRestTime: Int,
) {
    companion object {
        val defaultPrograms = listOf(
            TomatoProgram(
                name = "default",
                workTime = 25,
                restTime = 5,
                longRestTime = 30,
            ),
            TomatoProgram(
                name = "fase",
                workTime = 10,
                restTime = 1,
                longRestTime = 5,
            )
        )
    }
}
