package com.theideal.goride.utility

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.theideal.goride.R
import com.theideal.goride.model.Trip
import com.theideal.goride.ui.rider.RiderActivity
import timber.log.Timber

private const val CHANNEL_ID = "notification_channel1"

fun sendNotification(
    context: Context,
    title: String,
    body: String,
    trip: Trip,
    channelName: String
) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val intent = Intent(context, RiderActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)!!

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationManager.createNotificationChannel(channel)
    }
    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(title)
        .setContentText(body)
        .setContentIntent(pendingIntent)
        .setAutoCancel(false)
        .build()
    notificationManager.notify(getUniqueId(), notification)
    // check if the notification is sent with timber
    Timber.d("notification sent, title: $title, body: $body, $notificationManager")
}

private fun getUniqueId() = ((System.currentTimeMillis() % 10000).toInt())