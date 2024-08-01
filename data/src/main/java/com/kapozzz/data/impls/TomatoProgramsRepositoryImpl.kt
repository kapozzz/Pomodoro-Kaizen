package com.kapozzz.data.impls

import com.kapozzz.data.room.TasksDatabase
import com.kapozzz.domain.models.TomatoProgram
import com.kapozzz.domain.repositories.TomatoProgramsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TomatoProgramsRepositoryImpl @Inject constructor(
    private val database: TasksDatabase
): TomatoProgramsRepository {
    override fun getTomatoPrograms(): Flow<List<TomatoProgram>> {
        return database.tasksDao().getTomatoPrograms()
    }

    override suspend fun getTomatoProgramById(id: String): TomatoProgram {
        return database.tasksDao().getTomatoProgramById(id)
    }

    override suspend fun addTomatoProgram(program: TomatoProgram) {
        database.tasksDao().addTomatoProgram(program)
    }

    override suspend fun deleteTomatoProgram(id: String) {
        database.tasksDao().deleteTomatoProgram(id)
    }
}