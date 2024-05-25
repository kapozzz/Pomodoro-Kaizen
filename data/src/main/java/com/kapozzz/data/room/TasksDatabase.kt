package com.kapozzz.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kapozzz.domain.models.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(TasksTypeConverter::class)
abstract class TasksDatabase: RoomDatabase() {
    abstract fun tasksDao(): TasksDao
}