package com.theideal.goride.viewmodel.rider

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.CardViewData
import com.theideal.goride.model.FirebaseRiderModel

class RiderViewModel : ViewModel() {

    val data = FirebaseRiderModel()

    private val _rideServicesData = MutableLiveData<ArrayList<CardViewData>>()
    val rideServicesData: LiveData<ArrayList<CardViewData>>
        get() = _rideServicesData

    private val _navToAvailableTrip = MutableLiveData<Boolean>()
    val navToAvailableTrip: LiveData<Boolean>
        get() = _navToAvailableTrip
    private val _navToRideRequest = MutableLiveData<Boolean>()
    val navToRideRequest: LiveData<Boolean>
        get() = _navToRideRequest
    private val _navToTripSuggestions = MutableLiveData<Boolean>()
    val navToTripSuggestions: LiveData<Boolean>
        get() = _navToTripSuggestions
    private val _navToErrorFragment = MutableLiveData<Boolean>()
    val navToFragmentError: LiveData<Boolean>
        get() = _navToErrorFragment

    init {
        _navToAvailableTrip.value = false
        _navToRideRequest.value = false
        _navToTripSuggestions.value = false
        _navToErrorFragment.value = false
    }

    fun getRideServicesHome() {
        data.getRideServicesHome {
            _rideServicesData.value = it
        }
    }

    fun navTo(card: CardViewData?) {
        Log.i("TAG", "card!!.title")
        when (card!!.title) {
            "Available Trips" -> {
                _navToAvailableTrip.value = true
                Log.i("TAG", card.title)
            }
            "Ride Request" -> {
                _navToRideRequest.value = true
            }
            "Trip Suggestions" -> {
                _navToTripSuggestions.value = true
            }
            else -> _navToErrorFragment.value = true
        }

    }

    fun doneNavToAvailableTrip() {
        _navToAvailableTrip.value = false
    }

    fun doneNavToRideRequest() {
        _navToRideRequest.value = false
    }

    fun doneNavToTripSuggestions() {
        _navToTripSuggestions.value = false
    }

    fun doneNavToErrorFragment() {
        _navToErrorFragment.value = false
    }
}
