package com.theideal.goride.ui.driver.home.home_services.taxi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaxiViewModel : ViewModel() {

    private val _setAndGetDriverAvailablity = MutableLiveData<Boolean>()
    val setAndGetDriverAvailablity: LiveData<Boolean>
        get() = _setAndGetDriverAvailablity


    fun changeDriverStatus(){

    }
}