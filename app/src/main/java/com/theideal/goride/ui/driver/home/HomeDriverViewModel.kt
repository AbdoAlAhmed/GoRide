package com.theideal.goride.ui.driver.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.CardViewData
import com.theideal.goride.model.HomeDriverFirebase

class HomeDriverViewModel(private val homeDriverFirebase: HomeDriverFirebase) : ViewModel() {

    private val _homeDriverServices = MutableLiveData<ArrayList<CardViewData>>()
    val homeDriverServices: LiveData<ArrayList<CardViewData>>
        get() = _homeDriverServices

    fun getDriverServices() {
        homeDriverFirebase.getRideServicesHome {
            _homeDriverServices.value = it
        }
    }


}