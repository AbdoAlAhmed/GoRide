package com.theideal.goride.ui.driver.home.home_services.work_in_trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkInTripViewModel : ViewModel() {
    private val _setAndGetDriverAvailability = MutableLiveData<Boolean>()
    val setAndGetDriverAvailability: LiveData<Boolean>
        get() = _setAndGetDriverAvailability


    fun changeDriverAvailability() {
        _setAndGetDriverAvailability.value = true
    }
}