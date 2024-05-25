package com.kapozzz.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kapozzz.domain.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {
    @Query("SELECT * From Tasks")
    fun getTasks(): Flow<Task>
    @Query("SELECT * FROM Tasks WHERE id = :id")
    fun getTaskById(id: Int): Task
    @Insert
    fun addTask(task: Task)
    @Delete
    fun deleteTask(task: Task)
}