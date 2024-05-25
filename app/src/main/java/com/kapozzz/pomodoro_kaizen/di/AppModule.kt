package com.kapozzz.pomodoro_kaizen.di


import com.kapozzz.pomodoro_kaizen.navigation.NavigationProvider
import com.kapozzz.tasks.navigation.TasksApi
import com.kapozzz.timer.navigation.TimerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideNavigationProvider(
        timerApi: TimerApi,
        tasksApi: TasksApi
    ): NavigationProvider {
        return NavigationProvider(
            timer = timerApi,
            tasks = tasksApi
        )
    }
}