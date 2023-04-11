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
        dbFirebaseDriver.getAndUpdateUserInformation {
            Timber.i("Driver Status: $it")
            _setDriverAvailable.value = it.status != "not_available"
        }
    }
    fun changeDriverStatus() {
       if (_setDriverAvailable.value == true) {
           dbFirebaseDriver.getAndUpdateUserInformation ("status", "not_available") {
               _setDriverAvailable.value = false
           }
       } else {
           dbFirebaseDriver.getAndUpdateUserInformation ("status", "available") {
               _setDriverAvailable.value = true
           }
       }
    }


}