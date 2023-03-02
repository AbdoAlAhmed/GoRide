package com.theideal.goride.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.model.FirebaseAuthModel

class AuthenticationViewModelFactory(private val firebaseAuthModel: FirebaseAuthModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthenticationViewModel::class.java)) {
            return AuthenticationViewModel(firebaseAuthModel) as T
        }
        throw IllegalArgumentException(" something with factory")
    }
}