package com.kapozzz.domain.models

import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Stable
@Entity(tableName = "TomatoPrograms")
data class TomatoProgram(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val workTime: Int,
    val restTime: Int,
    val longRestTime: Int,
) {
    companion object {
        val defaultProgram = TomatoProgram(
            id = "default_program_1",
            name = "Default",
            workTime = 25,
            restTime = 5,
            longRestTime = 30,
        )
    }
}

enum class Tomato {
    Work, Rest, LongRest
}
