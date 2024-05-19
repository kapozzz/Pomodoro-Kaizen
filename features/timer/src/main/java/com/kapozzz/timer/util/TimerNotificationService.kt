package com.kapozzz.timer.util

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kapozzz.timer.R
import com.kapozzz.timer.di.TimerNotificationBuilder
import com.kapozzz.timer.di.TimerNotificationManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

// TimerNotificationService
const val TOMATO_PROGRESS_CHANNEL_ID = "tomato_progress_channel"
const val TOMATO_PROGRESS_CHANNEL_DESCRIPTION = "Pomodoro stage progress"

const val NOTIFICATION_PROGRESS_ID = 1

class TimerNotificationService @Inject constructor(
    @TimerNotificationManager private val notificationManager: NotificationManagerCompat,
    @TimerNotificationBuilder private val notificationBuilder: NotificationCompat.Builder,
    @ApplicationContext private val context: Context
) {

    private val PROGRESS_TITLE = context.getString(R.string.progress_notification_title)
    private val TIME_LEFT = context.getString(R.string.there_s_time_left_progress_notification_body)

    fun updateTime(newTime: String, isWorking: Boolean) {

        val intent = Intent(context, TimerReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = notificationBuilder
            .setContentTitle(PROGRESS_TITLE)
            .setContentText("$TIME_LEFT: $newTime")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setColor(Color.Red.toArgb())
            .setOngoing(true)
            .setDeleteIntent(
                pendingIntent
            )
            .clearActions()
            .addAction(
                0, if (isWorking) "Pause" else "Play", pendingIntent
            )
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(NOTIFICATION_PROGRESS_ID, notification)
    }

    fun clearTimeNotification() {
        notificationManager.cancel(NOTIFICATION_PROGRESS_ID)
    }

}