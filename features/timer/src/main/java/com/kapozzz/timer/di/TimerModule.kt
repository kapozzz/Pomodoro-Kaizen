package com.kapozzz.timer.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kapozzz.timer.navigation.TimerApi
import com.kapozzz.timer.navigation.TimerApiImpl
import com.kapozzz.timer.util.TOMATO_PROGRESS_CHANNEL_DESCRIPTION
import com.kapozzz.timer.util.TOMATO_PROGRESS_CHANNEL_ID
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TimerModule {
    @Provides
    fun provideDetailsApi(): TimerApi {
        return TimerApiImpl()
    }

    @TimerNotificationBuilder
    @Singleton
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, TOMATO_PROGRESS_CHANNEL_ID)
            .setContentTitle("Pomodoro Kaizen!")
            .setSmallIcon(com.kapozzz.ui.R.drawable.tomato_notify_icon)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TimerNotificationManager
    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                TOMATO_PROGRESS_CHANNEL_ID,
                TOMATO_PROGRESS_CHANNEL_DESCRIPTION,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
        return notificationManager
    }
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class TimerNotificationBuilder

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class TimerNotificationManager