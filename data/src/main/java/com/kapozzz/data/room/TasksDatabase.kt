package com.kapozzz.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kapozzz.domain.models.Task
import com.kapozzz.domain.models.TomatoProgram

@Database(
    entities = [Task::class, TomatoProgram::class],
    version = 11,
    exportSchema = false
)
@TypeConverters(TasksTypeConverter::class)
abstract class TasksDatabase: RoomDatabase() {
    abstract fun tasksDao(): TasksDao
}