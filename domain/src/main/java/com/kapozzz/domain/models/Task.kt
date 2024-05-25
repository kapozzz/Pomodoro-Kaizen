package com.kapozzz.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

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
    }
}
