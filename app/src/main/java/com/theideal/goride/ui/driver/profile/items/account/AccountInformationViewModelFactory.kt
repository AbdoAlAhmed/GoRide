package com.theideal.goride.ui.driver.profile.items.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AccountInformationViewModelFactory(
    private val db: AccountInformationFirebase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountInformationViewModel::class.java)) {
            return AccountInformationViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}