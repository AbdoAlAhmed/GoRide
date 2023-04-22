package com.theideal.goride.ui.driver.profile.items

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.ui.driver.home.home_services.work_in_trip.WorkInTripFirebase
import com.theideal.goride.ui.driver.home.home_services.work_in_trip.WorkInTripFragmentViewModel

class DestinationPreferenceViewModelFactory(
    private val db: DestinationPreferenceFirebase,
    private val app: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DestinationPreferenceViewModel::class.java)) {
            return DestinationPreferenceViewModel(db, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}