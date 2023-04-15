package com.theideal.goride.ui.driver.home.home_services.suggest_trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.TripsLine
import com.theideal.goride.model.User

class SuggestTripsViewModel(private val db: FirebaseSuggestTrips) : ViewModel() {


    private val _userInfo = MutableLiveData<User>()
    val userInfo: MutableLiveData<User>
        get() = _userInfo

    private val _listOFSuggestTrips = MutableLiveData<List<TripsLine>>()
    val listOfSuggestTrips: LiveData<List<TripsLine>>
        get() = _listOFSuggestTrips

    fun getAndUpdateUserInfo() {
        db.getAndUpdateUserInfo {
            _userInfo.value = it
        }
    }

    fun getSuggestTrips() {
        db.getSuggestTrips("tripsLineStatus", "suggest") {
            _listOFSuggestTrips.value = it
        }
    }


}