package com.theideal.goride

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.theideal.goride.model.User
import timber.log.Timber
import java.util.*

class MyApplication : Application() {

    private val user = User()
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
           Timber.i( "FCM token: $token")
            user.tokenId = token
        }.addOnFailureListener { e ->
           Timber.i( "some thing wrong here body: $e")
        }

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, api_Key, Locale.getDefault())
            Places.createClient(this)

        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}