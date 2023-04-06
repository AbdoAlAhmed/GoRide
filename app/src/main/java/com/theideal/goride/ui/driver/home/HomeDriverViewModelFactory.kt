package com.theideal.goride.ui.driver.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.model.HomeDriverFirebase

class HomeDriverViewModelFactory(private val homeDriverFirebase: HomeDriverFirebase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeDriverViewModel::class.java)){
            return HomeDriverViewModel(homeDriverFirebase) as T
        }
        throw IllegalArgumentException(" something wrong with factory")
    }
}