package com.theideal.goride.ui.rider.home.services.availableTrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.DriverStatus
import com.theideal.goride.model.FirebaseRiderModel
import com.theideal.goride.model.GeoFencing
import com.theideal.goride.model.Trip

class AvailableTripsViewModel : ViewModel() {
    val data = FirebaseRiderModel()

    private val _availableTrips = MutableLiveData<ArrayList<Trip>>()
    val availableTrips: LiveData<ArrayList<Trip>>
        get() = _availableTrips

    private val _addGeoFencing = MutableLiveData<ArrayList<GeoFencing>>()
    val addGeoFencing: LiveData<ArrayList<GeoFencing>>
        get() = _addGeoFencing
    private val _availableDrivers = MutableLiveData<ArrayList<DriverStatus>>()
    val availableDrivers: LiveData<ArrayList<DriverStatus>>
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
}