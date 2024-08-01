package com.kapozzz.data.di

import android.content.Context
import androidx.room.Room
import com.kapozzz.data.impls.TasksRepositoryImpl
import com.kapozzz.data.impls.TomatoProgramsRepositoryImpl
import com.kapozzz.data.room.TasksDatabase
import com.kapozzz.domain.repositories.TasksRepository
import com.kapozzz.domain.repositories.TomatoProgramsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TasksDataModule {
    @Provides
    @Singleton
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

    @Provides
    fun provideTomatoProgramsRepository(repo: TomatoProgramsRepositoryImpl): TomatoProgramsRepository {
        return repo
    }
}