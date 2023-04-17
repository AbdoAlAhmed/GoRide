package com.theideal.goride.ui.driver.home.home_services.work_in_trip

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WorkInTripFragmentViewModelFactory(
    private val db: WorkInTripFirebase,
    private val app: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkInTripFragmentViewModel::class.java)) {
            return WorkInTripFragmentViewModel(db, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}