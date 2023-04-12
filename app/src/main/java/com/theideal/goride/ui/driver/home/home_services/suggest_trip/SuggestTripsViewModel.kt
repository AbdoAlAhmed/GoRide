package com.theideal.goride.ui.driver.home.home_services.suggest_trip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SuggestTripsViewModel: ViewModel() {

    private val _setDriverAvailability = MutableLiveData<Boolean>()
    val setDriverAvailability: MutableLiveData<Boolean>
        get() = _setDriverAvailability


    fun changeDriverStatus(){

    }
}