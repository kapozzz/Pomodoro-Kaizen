package com.kapozzz.domain.repositories

import com.kapozzz.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getTasks(): Flow<Task>
    fun getTaskById(id: Int): Task
    fun addTask(task: Task)
    fun deleteTask(task: Task)
}