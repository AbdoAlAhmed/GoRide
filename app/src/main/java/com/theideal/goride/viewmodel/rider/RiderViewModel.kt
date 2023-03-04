package com.theideal.goride.viewmodel.rider

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.CardViewData
import com.theideal.goride.model.FirebaseRiderModel
import timber.log.Timber

class RiderViewModel : ViewModel() {
    enum class HomeServicesFragment { Available, Suggest, Request, Error }

    val data = FirebaseRiderModel()

    private val _rideServicesData = MutableLiveData<ArrayList<CardViewData>>()
    val rideServicesData: LiveData<ArrayList<CardViewData>>
        get() = _rideServicesData


    private val _navTo = MutableLiveData<HomeServicesFragment>()
    val navTo: LiveData<HomeServicesFragment>
        get() = _navTo

    init {

    }

    fun getRideServicesHome() {
        data.getRideServicesHome {
            _rideServicesData.value = it
        }
    }

    fun navTo(card: CardViewData?) {
        Timber.i(card?.title)
        when (card!!.title) {
            "Available Trips" -> {
                _navTo.value = HomeServicesFragment.Available
            }
            "Ride Request" -> {
                _navTo.value = HomeServicesFragment.Request
            }
            "Trip Suggestions" -> {
                _navTo.value = HomeServicesFragment.Suggest
            }
            else -> _navTo.value = HomeServicesFragment.Error
        }

    }

}
