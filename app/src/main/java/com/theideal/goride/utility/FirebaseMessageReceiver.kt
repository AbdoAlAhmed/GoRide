package com.theideal.goride.utility

import android.app.NotificationManager
import android.content.Context
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

        val notification = NotificationCompat.Builder(this, "<NOTIFICATION_CHANNEL_ID>")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(message.data["title"])
            .setContentText("message.data[\"body\"]")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)

    }
}