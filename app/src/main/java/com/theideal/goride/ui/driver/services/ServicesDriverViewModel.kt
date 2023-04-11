package com.theideal.goride.ui.driver.services

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.CardViewData

class ServicesDriverViewModel(private val db: ServicesDriverFirebase) : ViewModel() {
    private val localData = arrayListOf(
        CardViewData(
            1,
            "Loyalty Points",
            " Earn Rewards for Engaging with Our App ",
            "null",
            ""
        )
    )

    private val _servicesDriver = MutableLiveData<ArrayList<CardViewData>>()
    val servicesDriver: MutableLiveData<ArrayList<CardViewData>>
        get() = _servicesDriver


    init {
        _servicesDriver.value = localData
    }


    fun getServicesDriver_() {
        db.getDriverServices_ {
            if (!_servicesDriver.value!!.containsAll(it)) {
                _servicesDriver.value!!.addAll(it)
            }
        }
    }
}