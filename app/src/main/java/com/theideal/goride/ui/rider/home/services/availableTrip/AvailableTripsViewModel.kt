package com.theideal.goride.ui.rider.home.services.availableTrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.*

class AvailableTripsViewModel : ViewModel() {
    private val riderData = FirebaseRiderModel()
    private val authData = FirebaseAuthModel()


    private val _availableTrips = MutableLiveData<ArrayList<Trip>>()
    val availableTrips: LiveData<ArrayList<Trip>>
        get() = _availableTrips

    private val _addGeoFencing = MutableLiveData<ArrayList<GeoFencing>>()
    val addGeoFencing: LiveData<ArrayList<GeoFencing>>
        get() = _addGeoFencing
    private val _availableDrivers = MutableLiveData<ArrayList<User>>()
    val availableDrivers: LiveData<ArrayList<User>>
        get() = _availableDrivers
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user


    fun initializeAvailableTrips() {
        riderData.getAvailableTrips {
            _availableTrips.value = it
        }
    }

    fun getAvailableDriver() {
        // you can restrict the incoming data
        riderData.getDriver { driver ->
            _availableDrivers.value = driver
        }
    }

    fun addRiderIdToAvailableTrip(riderId: String) {
        riderData.addRiderIdToAvailableTrip(riderId)
    }
    fun setAvailableTrip(trip:Trip){
        riderData.setAvailableTrip(trip)
    }

    fun getUser() {
        authData.getUser {
            _user.value = it
        }
    }
}