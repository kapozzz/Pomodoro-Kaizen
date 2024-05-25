package com.kapozzz.tasks.di

import com.kapozzz.tasks.navigation.InternalTaskFeatureApi
import com.kapozzz.tasks.navigation.TasksApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class TasksModule {
    @Provides
    fun provideTasksApi() : TasksApi {
        return InternalTaskFeatureApi()
    }
}