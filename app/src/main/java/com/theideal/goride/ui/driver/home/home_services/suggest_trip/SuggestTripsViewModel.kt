package com.theideal.goride.ui.driver.home.home_services.suggest_trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.TripsLine
import com.theideal.goride.model.User
import timber.log.Timber

class SuggestTripsViewModel(private val db: FirebaseSuggestTrips) : ViewModel() {


    private val _userInfo = MutableLiveData<User>()
    val userInfo: MutableLiveData<User>
        get() = _userInfo

    private val _listOFSuggestTrips = MutableLiveData<ArrayList<TripsLine>>()
    val listOfSuggestTrips: LiveData<ArrayList<TripsLine>>
        get() = _listOFSuggestTrips

    fun getAndUpdateUserInfo() {
        db.getAndUpdateUserInfo {
            _userInfo.value = it
        }
    }

    fun getSuggestTrips() {
        Timber.i("getSuggestTrips clicked ")
        db.getSuggestTrips("tripsLineStatus", "suggest") {
            _listOFSuggestTrips.value = it
        }
    }


}