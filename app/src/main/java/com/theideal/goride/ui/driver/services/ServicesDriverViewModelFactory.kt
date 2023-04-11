package com.theideal.goride.ui.driver.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ServicesDriverViewModelFactory(private val servicesFirebase: ServicesDriverFirebase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServicesDriverViewModel::class.java)) {
            return ServicesDriverViewModel(servicesFirebase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}