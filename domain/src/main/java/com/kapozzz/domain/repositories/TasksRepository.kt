package com.kapozzz.domain.repositories

import com.kapozzz.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun getTasks(): Flow<List<Task>>
    suspend fun getTaskById(id: String): Task
    suspend fun addTask(task: Task)
    suspend fun deleteTask(task: Task)
}