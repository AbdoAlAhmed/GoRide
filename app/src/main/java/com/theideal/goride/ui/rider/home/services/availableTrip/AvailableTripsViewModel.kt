package com.theideal.goride.ui.rider.home.services.availableTrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.*

class AvailableTripsViewModel(
    private val riderData: FirebaseRiderModel, private val authData: FirebaseAuthModel
) : ViewModel() {


    private val _availableTrips = MutableLiveData<ArrayList<Trip>>()
    val availableTrips: LiveData<ArrayList<Trip>>
        get() = _availableTrips

    private val _addGeoFencing = MutableLiveData<ArrayList<GeoFencing>>()
    val addGeoFencing: LiveData<ArrayList<GeoFencing>>
        get() = _addGeoFencing
    private val _availableDriver = MutableLiveData<User>()
    val availableDriver: LiveData<User>
        get() = _availableDriver
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _navToAvailableTrip = MutableLiveData<Boolean>()
    val navToAvailableTrip: LiveData<Boolean>
        get() = _navToAvailableTrip





    fun navToAvailableTripMaps() {
        _navToAvailableTrip.value = true
    }

    fun navToAvailableTripCompleteMaps() {
        _navToAvailableTrip.value = false
    }

    fun cancelTrip() {
        navToAvailableTripCompleteMaps()
        // cancel trip remove rider id from trip
    }



    fun initializeAvailableTrips() {
        riderData.getAvailableTrips {
            _availableTrips.value = it
        }
    }

    fun getOrRequestDriver() {
        // you can restrict the incoming data
        riderData.getOrRequestDriver("from","cairo","to","egypt") { driver ->
            _availableDriver.value = driver
        }
    }

    fun addRiderIdToAvailableTrip(riderId: String) {
        riderData.addRiderIdToAvailableTrip(riderId)
    }

    fun setAvailableTrip(trip: Trip) {
        riderData.setAvailableTrip(trip)
    }

    fun getOrCreateTrip(trip: Trip) {
        riderData.getOrCreateTrip(trip)
    }

    fun getUser() {
        authData.getUser {
            _user.value = it
        }
    }

}