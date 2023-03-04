package com.theideal.goride

import android.app.Application
import com.google.android.libraries.places.api.Places
import timber.log.Timber
import java.util.*

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, api_Key, Locale.getDefault())
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}