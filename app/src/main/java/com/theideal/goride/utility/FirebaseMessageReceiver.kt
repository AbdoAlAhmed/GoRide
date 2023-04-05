package com.theideal.goride.utility

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.theideal.goride.R
import timber.log.Timber

class FirebaseMessageReceiver : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Timber.i("new token: $p0")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Timber.i("message received: ${message.data}")

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var channelName = "channelFireBase"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "CHANNEL_ID",
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(message.data["title"])
            .setContentText("message.data[\"body\"]")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        fun getUniqueId() = ((System.currentTimeMillis() % 10000).toInt())
        notificationManager.notify(getUniqueId(), notification)

    }
}