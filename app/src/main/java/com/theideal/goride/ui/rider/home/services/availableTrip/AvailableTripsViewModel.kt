package com.theideal.goride.ui.rider.home.services.availableTrip

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.*
import com.theideal.goride.utility.sendNotification
import timber.log.Timber

class AvailableTripsViewModel(
    private val riderData: FirebaseRiderModel, private val authData: FirebaseAuthModel
) : ViewModel() {


    private val _availableTrips = MutableLiveData<ArrayList<Trip>>()
    val availableTrips: LiveData<ArrayList<Trip>>
        get() = _availableTrips

    private val _addGeoFencing = MutableLiveData<ArrayList<GeoFencing>>()
    val addGeoFencing: LiveData<ArrayList<GeoFencing>>
        get() = _addGeoFencing
    private val _driverInfo = MutableLiveData<User>()
    val driverInfo: LiveData<User>
        get() = _driverInfo
    private val _carInfo = MutableLiveData<Car>()
    val carInfo: LiveData<Car>
        get() = _carInfo
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
        riderData.getOrRequestDriver("from", "cairo", "to", "egypt") { user, driver ->
            Timber.i("driver: $driver")
            _driverInfo.value = user
            _carInfo.value = driver
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
        authData.getAndUpdateUserInfo {
            _user.value = it
        }
    }


    fun senNotification(context: Context, trip: Trip) {
        sendNotification(
            context = context,
            title = "name",
            body = "How are you",
            trip = trip,
            channelName = "channel name1"
        )
    }

}