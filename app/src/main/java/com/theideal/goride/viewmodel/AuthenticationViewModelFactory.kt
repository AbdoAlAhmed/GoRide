package com.theideal.goride.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.model.FirebaseModel

class AuthenticationViewModelFactory(private val firebaseModel: FirebaseModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthenticationViewModel::class.java)) {
            return AuthenticationViewModel(firebaseModel) as T
        }
        throw IllegalArgumentException(" something with factory")
    }
}