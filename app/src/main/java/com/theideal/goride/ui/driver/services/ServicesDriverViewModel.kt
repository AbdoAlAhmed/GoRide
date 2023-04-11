package com.theideal.goride.ui.driver.services

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.CardViewData
import com.theideal.goride.model.ServicesDriverFirebase

class ServicesDriverViewModel(private val db: ServicesDriverFirebase) : ViewModel() {
    private val localData = arrayListOf(
        CardViewData(
            1,
            "Ride",
            "Get a ride",
            "null",
            ""
        ),
        CardViewData(
            2,
            "Ride",
            "Get a ride",
            "null",
            ""
        ),
        CardViewData(
            3,
            "Ride",
            "Get a ride",
            "null",
            ""
        ),
    )

    private val _servicesDriver = MutableLiveData<ArrayList<CardViewData>>()
    val servicesDriver: MutableLiveData<ArrayList<CardViewData>>
        get() = _servicesDriver


    init {
        _servicesDriver.value = localData
    }


    fun getServicesDriver1() {
        db.getRideServicesServicesFragment {
            _servicesDriver.value!!.addAll(it)
        }
    }
}