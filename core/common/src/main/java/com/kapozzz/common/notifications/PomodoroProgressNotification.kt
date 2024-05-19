package com.kapozzz.common.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.kapozzz.ui.R

class PomodoroProgressNotification(
    private val context: Context
) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(timeRemaining: String) {
        val notification = NotificationCompat.Builder(context, POMODORO_PROGRESS_CHANNEL_ID)
            .setSmallIcon(R.drawable.tomato_notify_icon)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .setContentTitle("Stage progress")
            .setContentText("Time remaining: $timeRemaining")
            .build()

        notificationManager.notify(
            1,
            notification
        )
    }

    companion object {
        const val POMODORO_PROGRESS_CHANNEL_ID = "progress_channel"
    }
}