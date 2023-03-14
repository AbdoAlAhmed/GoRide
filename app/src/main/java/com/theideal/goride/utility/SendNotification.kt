package com.theideal.goride.utility

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TaskStackBuilder
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.theideal.goride.model.Trip
import com.theideal.goride.ui.rider.home.services.availableTrip.AvailableTripsActivity

private const val CHANNEL_ID = "channel"

class SendNotification(context: Context, trip: Trip, channelName: String) {
    val notificationManager = context
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @RequiresApi(Build.VERSION_CODES.O)
    val channel = NotificationChannel(
        CHANNEL_ID,
        channelName,
        NotificationManager.IMPORTANCE_DEFAULT
    )


}