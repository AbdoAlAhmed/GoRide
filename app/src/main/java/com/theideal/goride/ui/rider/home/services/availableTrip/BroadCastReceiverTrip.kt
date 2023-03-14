package com.theideal.goride.ui.rider.home.services.availableTrip

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.Geofence.*
import com.google.android.gms.location.GeofencingEvent
import timber.log.Timber

class BroadCastReceiverTrip : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != null) {
            Timber.i("null")
            val geofenceEvent = GeofencingEvent.fromIntent(intent)
            if (geofenceEvent!!.hasError()) {
                Timber.i("there is an error ${geofenceEvent!!.errorCode}")
                return
            }
            when (geofenceEvent.geofenceTransition) {
                GEOFENCE_TRANSITION_DWELL -> {
                    Timber.i("dwell")
                }
                GEOFENCE_TRANSITION_ENTER -> {
                    Timber.i("enter")
                }
                GEOFENCE_TRANSITION_EXIT -> {
                    Timber.i("exit")
                }
            }
        }

    }
}