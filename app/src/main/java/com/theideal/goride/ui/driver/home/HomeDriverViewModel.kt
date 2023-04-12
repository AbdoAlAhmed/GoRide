package com.theideal.goride.ui.driver.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.CardViewData

class HomeDriverViewModel(private val homeDriverFragmentFirebase: HomeDriverFragmentFirebase) :
    ViewModel() {
    private val localData = arrayListOf(
        CardViewData(
            id = 1,
            title = "Work In A Specific Trip",
            subtitle = "choose which line to work on it",
            image = "null",
            label = ""
        ),

        CardViewData(
            id = 2,
            title = "Trip Suggestions",
            subtitle = "Suggest popular destinations",
            image = "null",
            label = ""
        ),
        CardViewData(
            id = 3,
            title = "Work In A Taxi",
            subtitle = "Drive with us and earn money on your own schedule!" +
                    " Be your own boss and enjoy the flexibility of choosing" +
                    " when and where you work. Join our community of drivers" +
                    " and help people get to their destinations safely and comfortably.",
            image = "null",
            label = "coming soon"
        )
    )


    enum class HomeDriverServices { WorkInASpecificTrip, WorkInATaxi, Suggest, Error, Done }

    private val _homeDriverServices = MutableLiveData<ArrayList<CardViewData>>()
    val homeDriverServices: LiveData<ArrayList<CardViewData>>
        get() = _homeDriverServices

    private val _navTo = MutableLiveData<HomeDriverServices>()
    val navTo: LiveData<HomeDriverServices>
        get() = _navTo

    private val _snackBar = MutableLiveData<String>()
    val snackBar: LiveData<String>
        get() = _snackBar


    init {
        _homeDriverServices.value = localData
    }

    fun getDriverServices() {
        homeDriverFragmentFirebase.getRideServicesHome {
            if (!_homeDriverServices.value!!.containsAll(it)) {
                _homeDriverServices.value!!.addAll(it)
            }
        }
    }


    fun navTo(card: CardViewData?) {
        when (card!!.id) {
            1 -> {
                _navTo.value = HomeDriverServices.WorkInASpecificTrip
            }
            2 -> {
                _navTo.value = HomeDriverServices.Suggest
            }
            3 -> {
                _navTo.value = HomeDriverServices.WorkInATaxi
            }
            else -> {
                _navTo.value = HomeDriverServices.Error
                _snackBar.value = "Error"
            }
        }

    }

    fun doneNavigating() {
        _navTo.value = HomeDriverServices.Done
    }

    fun doneShowingSnackBar() {
        _snackBar.value = ""
    }
}