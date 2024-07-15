package com.kapozzz.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kapozzz.domain.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {
    @Query("SELECT * From Tasks")
    fun getUncompletedTasks(): Flow<List<Task>>
    @Query("SELECT * FROM Tasks WHERE id = :id")
    suspend fun getTaskById(id: String): Task
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: Task)
    @Query("DELETE FROM Tasks WHERE id = :id")
    suspend fun deleteTask(id: String)
}