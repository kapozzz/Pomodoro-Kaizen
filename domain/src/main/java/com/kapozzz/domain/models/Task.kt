package com.kapozzz.domain.models

import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Stable
@Entity(tableName = "Tasks")
data class Task(
    @PrimaryKey val id: String,
    val name: String,
    val steps: List<Step>,
    val program: TomatoProgram,
    val deadline: Long
) {
    val isCompleted: Boolean
        get() = steps.count { !it.isCompleted } == 0

    companion object {
        fun generateId(): String = UUID.randomUUID().toString()
        fun getDefault(): Task {
            return Task(
                id = "default_empty_id",
                name = "",
                steps = listOf(Step.getCompletedEmptyStep()),
                program = TomatoProgram.defaultProgram,
                deadline = 0L
            )
        }
    }
}
