package com.theideal.goride.ui.driver.services

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.CardViewData
import com.theideal.goride.model.ServicesDriverFirebase

class ServicesDriverViewModel(private val db:ServicesDriverFirebase) : ViewModel(){

    private val _servicesDriver = MutableLiveData<ArrayList<CardViewData>>()
    val servicesDriver: MutableLiveData<ArrayList<CardViewData>>
        get() = _servicesDriver




    fun getServicesDriver1(){
        db.getRideServicesServicesFragment {
            _servicesDriver.value = it
        }
    }
}