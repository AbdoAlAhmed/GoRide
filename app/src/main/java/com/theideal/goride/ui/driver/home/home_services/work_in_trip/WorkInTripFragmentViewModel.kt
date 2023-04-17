package com.theideal.goride.ui.driver.home.home_services.work_in_trip

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.TripsLine
import com.theideal.goride.model.User

class WorkInTripFragmentViewModel(
    private val db: WorkInTripFirebase,
    private val app: Application
) : ViewModel() {

    private val _tripsLineList = MutableLiveData<ArrayList<TripsLine>>()
    val tripsLineList: MutableLiveData<ArrayList<TripsLine>>
        get() = _tripsLineList

    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User>
        get() = _userInfo

    private val _isChecked = MutableLiveData<Boolean>()
    val isChecked: LiveData<Boolean>
        get() = _isChecked

    init {
        _isChecked.value = false
    }

    fun getTripData() {
        db.getTripData { tripsLineList ->
            _tripsLineList.value = tripsLineList
        }
    }

    fun userInfo() {
        db.getAndUpdateUserInfo { user ->
            _userInfo.value = user
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun addAndRemoveTrip(tripsLine: TripsLine) {
        if (_isChecked.value == true) {
            db.addTrip(tripsLine.tripsLineId)

        } else {
            db.removeTrip(tripsLine.tripsLineId)
        }
    }
}