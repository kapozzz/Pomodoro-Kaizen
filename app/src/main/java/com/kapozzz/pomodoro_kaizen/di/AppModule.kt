package com.kapozzz.pomodoro_kaizen.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import com.kapozzz.common.notifications.PomodoroProgressNotification
import com.kapozzz.pomodoro_kaizen.navigation.NavigationProvider
import com.kapozzz.timer.navigation.TimerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideNavigationProvider(
        timerApi: TimerApi
    ): NavigationProvider {
        return NavigationProvider(
            timer = timerApi
        )
    }
    @Singleton
    @Provides
    fun provideProgressNotification(@ApplicationContext context: Context): PomodoroProgressNotification {
        return PomodoroProgressNotification(context)
    }
}