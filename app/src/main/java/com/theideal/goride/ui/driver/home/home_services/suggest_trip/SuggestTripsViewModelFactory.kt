package com.theideal.goride.ui.driver.home.home_services.suggest_trip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SuggestTripsViewModelFactory(private val db: FirebaseSuggestTrips) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SuggestTripsViewModel::class.java)) {
            return SuggestTripsViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
