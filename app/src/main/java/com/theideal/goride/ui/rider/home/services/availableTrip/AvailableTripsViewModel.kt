package com.theideal.goride.ui.rider.home.services.availableTrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.*

class AvailableTripsViewModel : ViewModel() {
    val data = FirebaseRiderModel()

    private val _availableTrips = MutableLiveData<ArrayList<Trip>>()
    val availableTrips: LiveData<ArrayList<Trip>>
        get() = _availableTrips

    private val _addGeoFencing = MutableLiveData<ArrayList<GeoFencing>>()
    val addGeoFencing: LiveData<ArrayList<GeoFencing>>
        get() = _addGeoFencing
    private val _availableDrivers = MutableLiveData<ArrayList<User>>()
    val availableDrivers: LiveData<ArrayList<User>>
        get() = _availableDrivers


    fun initializeAvailableTrips() {
        data.getAvailableTrips {
            _availableTrips.value = it
        }
    }

    fun getAvailableDriver() {
        data.getDriver { driver ->
            _availableDrivers.value = driver
        }
    }

    fun requestAvailableTrip(tripId: String, trip: Trip) {
        data.requestFromAvailableTrips(tripId, trip)
    }
}