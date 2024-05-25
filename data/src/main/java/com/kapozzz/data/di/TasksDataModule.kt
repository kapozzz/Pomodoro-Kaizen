package com.kapozzz.data.di

import android.content.Context
import androidx.room.Room
import com.kapozzz.data.room.TasksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class TasksDataModule {
    @Provides
    fun provideTasksDatabase(@ApplicationContext context: Context): TasksDatabase {
        return Room.databaseBuilder(
            context,
            TasksDatabase::class.java, "tasks-database"
        ).build()
    }
}