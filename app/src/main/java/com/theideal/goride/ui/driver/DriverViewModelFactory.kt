package com.theideal.goride.ui.driver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DriverViewModelFactory(private val dbDriverHomeActivityFirebase: DriverHomeActivityFirebase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DriverViewModel::class.java)) {
            return DriverViewModel(dbDriverHomeActivityFirebase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
