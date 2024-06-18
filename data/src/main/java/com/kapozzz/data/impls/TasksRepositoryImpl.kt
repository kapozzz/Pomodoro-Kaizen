package com.kapozzz.data.impls

import com.kapozzz.data.room.TasksDatabase
import com.kapozzz.domain.models.Task
import com.kapozzz.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    database: TasksDatabase
) : TasksRepository {
    
    private val dao = database.tasksDao()

    override suspend fun getTasks(): Flow<List<Task>> {
        return dao.getTasks()
    }

    override suspend fun getTaskById(id: String): Task {
        return dao.getTaskById(id)
    }

    override suspend fun addTask(task: Task) {
        dao.addTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }
}