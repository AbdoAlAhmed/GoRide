package com.theideal.goride.ui.driver.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProfileDriverViewModelFactory(private val db: ProfileDriverFirebase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileDriverViewModel::class.java)) {
            return ProfileDriverViewModel(db) as T
        }
        throw IllegalArgumentException("something wrong with factory")
    }
}