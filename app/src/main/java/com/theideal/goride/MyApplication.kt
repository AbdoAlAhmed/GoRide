package com.theideal.goride

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.places.api.Places
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.viewmodel.auth.AuthenticationViewModel
import com.theideal.goride.viewmodel.auth.AuthenticationViewModelFactory
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