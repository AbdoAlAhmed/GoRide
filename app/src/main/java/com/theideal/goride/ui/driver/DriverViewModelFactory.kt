package com.theideal.goride.ui.driver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DriverViewModelFactory(private val dbFirebaseDriver: FirebaseDriver) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DriverViewModel::class.java)) {
            return DriverViewModel(dbFirebaseDriver) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
