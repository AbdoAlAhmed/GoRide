package com.theideal.goride.ui.driver.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.User

class ProfileDriverViewModel(val db: ProfileDriverFirebase) : ViewModel() {
    private val localData = arrayListOf(
        Setting(nameOfTheSetting = "Setting", label = "")
        ,
        Setting(nameOfTheSetting = "Destination Preferences", label = ""),
        Setting(nameOfTheSetting = "Payment", label = "Under Development ..."),
        Setting(nameOfTheSetting = "Notifications", label = ""),
        Setting(nameOfTheSetting = "Help", label = ""),
        Setting(nameOfTheSetting = "About", label = ""),
        Setting(nameOfTheSetting = "Privacy Policy", label = ""),
        Setting(nameOfTheSetting = "Logout", label = "")

    )
    private var _profileUser = MutableLiveData<User>()
    val profileUser: LiveData<User>
        get() = _profileUser

    private var _settings = MutableLiveData<List<Setting>>()
    val settings: LiveData<List<Setting>>
        get() = _settings


    init {
        _settings.value = localData
    }
    fun getAndUpdateUserInformation(vararg keyValue: String) {
        if (keyValue.size % 2 != 0) {
            throw IllegalArgumentException("keyValue must be even")
        } else {
            db.getAndUpdateUserInformation(*keyValue) {
                _profileUser.value = it
            }
        }
    }
}