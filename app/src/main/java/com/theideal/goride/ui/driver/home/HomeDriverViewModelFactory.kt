package com.theideal.goride.ui.driver.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeDriverViewModelFactory(private val homeDriverFragmentFirebase: HomeDriverFragmentFirebase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeDriverViewModel::class.java)){
            return HomeDriverViewModel(homeDriverFragmentFirebase) as T
        }
        throw IllegalArgumentException(" something wrong with factory")
    }
}