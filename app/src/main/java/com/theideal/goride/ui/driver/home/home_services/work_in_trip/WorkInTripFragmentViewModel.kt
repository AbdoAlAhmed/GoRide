package com.theideal.goride.ui.driver.home.home_services.work_in_trip

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.TripsLine

class WorkInTripFragmentViewModel(
    private val db: WorkInTripFirebase,
    private val app: Application
) : ViewModel() {

    private val _tripsLineList = MutableLiveData<ArrayList<TripsLine>>()
    val tripsLineList: MutableLiveData<ArrayList<TripsLine>>
        get() = _tripsLineList


    fun getTripData() {
        db.getTripData { tripsLineList ->
            _tripsLineList.value = tripsLineList
        }
    }
}