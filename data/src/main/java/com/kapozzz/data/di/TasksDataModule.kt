package com.kapozzz.data.di

import android.content.Context
import androidx.room.Room
import com.kapozzz.data.impls.TasksRepositoryImpl
import com.kapozzz.data.room.TasksDatabase
import com.kapozzz.domain.repositories.TasksRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TasksDataModule {
    @Provides
    fun provideTasksDatabase(@ApplicationContext context: Context): TasksDatabase {
        return Room.databaseBuilder(
            context,
            TasksDatabase::class.java, "tasks-database"
        ).build()
    }

    @Binds
    abstract fun provideTasksRepository(repo: TasksRepositoryImpl): TasksRepository
}