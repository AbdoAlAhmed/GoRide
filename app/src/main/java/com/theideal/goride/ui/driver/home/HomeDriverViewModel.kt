package com.theideal.goride.ui.driver.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.CardViewData
import com.theideal.goride.model.HomeDriverFirebase

class HomeDriverViewModel(private val homeDriverFirebase: HomeDriverFirebase) : ViewModel() {

    enum class HomeDriverServices{WorkInASpecificTrip , WorkInATaxi , Suggest , Error , Done}

    private val _homeDriverServices = MutableLiveData<ArrayList<CardViewData>>()
    val homeDriverServices: LiveData<ArrayList<CardViewData>>
        get() = _homeDriverServices

    private val _navTo = MutableLiveData<HomeDriverServices>()
    val navTo: LiveData<HomeDriverServices>
        get() = _navTo



    fun getDriverServices() {
        homeDriverFirebase.getRideServicesHome {
            _homeDriverServices.value = it
        }
    }


    fun navTo(card: CardViewData?) {
        when (card!!.title) {
            "Work In A Taxi" -> {
                _navTo.value = HomeDriverServices.WorkInATaxi
            }
            "Work In A Specific Trip" -> {
                _navTo.value = HomeDriverServices.WorkInASpecificTrip
            }
            "Trip Suggestions" -> {
                _navTo.value = HomeDriverServices.Suggest
            }
            else -> _navTo.value = HomeDriverServices.Error
        }

    }
    fun doneNavigating() {
        _navTo.value = HomeDriverServices.Done
    }

}