package com.kapozzz.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.kapozzz.domain.models.Task


class TasksTypeConverter {
    @TypeConverter
    fun fromTaskToJson(task: Task): String = Gson().toJson(task)
    @TypeConverter
    fun fromJsonToTask(json: String): Task = Gson().fromJson(json, Task::class.java)
}