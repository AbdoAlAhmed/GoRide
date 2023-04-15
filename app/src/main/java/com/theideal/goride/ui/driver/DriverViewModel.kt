package com.theideal.goride.ui.driver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class DriverViewModel(private val dbDriverHomeActivityFirebase: DriverHomeActivityFirebase) :
    ViewModel() {
    private val _setDriverAvailable = MutableLiveData<Boolean>()
    val setDriverAvailable: LiveData<Boolean>
        get() = _setDriverAvailable


    fun setDriverAvailable() {
        dbDriverHomeActivityFirebase.getAndUpdateUserInfo() {
            Timber.i("Driver Status: $it")
            _setDriverAvailable.value = it.status != "unavailable"
        }
    }

    fun changeDriverStatus() {
        if (_setDriverAvailable.value == true) {
            dbDriverHomeActivityFirebase.getAndUpdateUserInfo("status", "unavailable") {
                _setDriverAvailable.value = false
            }
        } else {
            dbDriverHomeActivityFirebase.getAndUpdateUserInfo("status", "available") {
                _setDriverAvailable.value = true
            }
        }
    }


}