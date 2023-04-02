package com.theideal.goride.ui.rider.home.services.availableTrip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.FirebaseRiderModel

class AvailableTripsViewModelFactory(
    private val riderData: FirebaseRiderModel,
    private val authData: FirebaseAuthModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AvailableTripsViewModel::class.java)) {
            return AvailableTripsViewModel(riderData, authData) as T
        }
        throw IllegalArgumentException(" something with factory")
    }
}
