package com.kapozzz.domain.repositories

import com.kapozzz.domain.models.TomatoProgram
import kotlinx.coroutines.flow.Flow

interface TomatoProgramsRepository {

    fun getTomatoPrograms(): Flow<List<TomatoProgram>>

    suspend fun getTomatoProgramById(id: String): TomatoProgram

    suspend fun addTomatoProgram(program: TomatoProgram)

    suspend fun deleteTomatoProgram(id: String)

}