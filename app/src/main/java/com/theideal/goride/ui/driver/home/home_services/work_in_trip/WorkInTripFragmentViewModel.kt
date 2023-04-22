package com.theideal.goride.ui.driver.home.home_services.work_in_trip

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.TripsLine
import com.theideal.goride.model.User
import timber.log.Timber

class WorkInTripFragmentViewModel(
    private val db: WorkInTripFirebase,
    private val app: Application
) : ViewModel() {

    private val _tripsLineList = MutableLiveData<ArrayList<TripsLine>>()
    val tripsLineList: LiveData<ArrayList<TripsLine>>
        get() = _tripsLineList


    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User>
        get() = _userInfo








}