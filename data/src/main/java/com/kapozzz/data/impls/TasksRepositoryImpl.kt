package com.kapozzz.data.impls

import com.kapozzz.data.room.TasksDatabase
import com.kapozzz.domain.models.Task
import com.kapozzz.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val database: TasksDatabase
) : TasksRepository {
    
    private val dao = database.tasksDao()

    override fun getTasks(): Flow<Task> {
        return dao.getTasks()
    }

    override fun getTaskById(id: Int): Task {
        return dao.getTaskById(id)
    }

    override fun addTask(task: Task) {
        dao.addTask(task)
    }

    override fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }
}