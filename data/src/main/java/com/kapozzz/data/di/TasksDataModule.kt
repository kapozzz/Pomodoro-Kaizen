package com.kapozzz.data.di

import android.content.Context
import androidx.room.Room
import com.kapozzz.data.impls.TasksRepositoryImpl
import com.kapozzz.data.room.TasksDatabase
import com.kapozzz.domain.repositories.TasksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TasksDataModule {
    @Provides
    fun provideTasksDatabase(@ApplicationContext context: Context): TasksDatabase {
        return Room.databaseBuilder(
            context,
            TasksDatabase::class.java, "tasks-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTasksRepository(repo: TasksRepositoryImpl): TasksRepository {
        return repo
    }
}