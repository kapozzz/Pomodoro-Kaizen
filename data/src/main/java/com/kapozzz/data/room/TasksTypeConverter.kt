package com.kapozzz.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kapozzz.domain.models.Step
import com.kapozzz.domain.models.Task
import com.kapozzz.domain.models.TomatoProgram

class TasksTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromTaskToJson(task: Task): String = gson.toJson(task)

    @TypeConverter
    fun fromJsonToTask(json: String): Task = gson.fromJson(json, Task::class.java)

    @TypeConverter
    fun fromStepsListToJson(steps: List<Step>): String = gson.toJson(steps)

    @TypeConverter
    fun fromJsonToStepsList(json: String): List<Step> {
        val listType = object : TypeToken<List<Step>>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun fromTomatoProgramToString(program: TomatoProgram): String = gson.toJson(program)

    @TypeConverter
    fun fromStringToTomatoProgram(json: String): TomatoProgram =
        gson.fromJson(json, TomatoProgram::class.java)
}