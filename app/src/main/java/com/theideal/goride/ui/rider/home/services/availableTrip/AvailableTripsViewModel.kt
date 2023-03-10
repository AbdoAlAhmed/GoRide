package com.theideal.goride.ui.rider.home.services.availableTrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.FirebaseRiderModel
import com.theideal.goride.model.Trip

class AvailableTripsViewModel : ViewModel() {
    val data = FirebaseRiderModel()

    private val _availableTrips = MutableLiveData<ArrayList<Trip>>()
    val availableTrips: LiveData<ArrayList<Trip>>
        get() = _availableTrips


    fun setData(){
        data.getAvailableTrips {
            _availableTrips.value = it
        }
    }
}