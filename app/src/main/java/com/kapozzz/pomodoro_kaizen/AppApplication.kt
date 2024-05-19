package com.kapozzz.pomodoro_kaizen

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.kapozzz.common.notifications.PomodoroProgressNotification
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                PomodoroProgressNotification.POMODORO_PROGRESS_CHANNEL_ID,
                "Pomodoro progress",
                NotificationManager.IMPORTANCE_LOW
            )
            channel.description = "Shows the progress of the current pomodoro stage"

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}