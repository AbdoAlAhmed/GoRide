package com.theideal.goride.ui.driver.home.home_services.suggest_trip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.User

class SuggestTripsViewModel(private val db: FirebaseSuggestTrips) : ViewModel() {


    private val _userInfo = MutableLiveData<User>()
    val userInfo: MutableLiveData<User>
        get() = _userInfo

    fun getAndUpdateUserInfo() {
        db.getAndUpdateUserInfo {
            _userInfo.value = it
        }
    }


}