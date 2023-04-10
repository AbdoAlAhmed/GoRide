package com.theideal.goride.ui.driver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class DriverViewModel(private val dbFirebaseDriver: FirebaseDriver) : ViewModel() {
    private val _setDriverAvailable = MutableLiveData<Boolean>()
    val setDriverAvailable: LiveData<Boolean>
        get() = _setDriverAvailable


    fun setDriverAvailable() {
        dbFirebaseDriver.getUserInfo {
            Timber.i("Driver Status: $it")
            _setDriverAvailable.value = it == "available"
        }
    }


}