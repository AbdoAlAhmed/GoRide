package com.theideal.goride.ui.driver.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.User

class ProfileDriverViewModel(val db: ProfileDriverFirebase) : ViewModel() {
    private val localData = arrayListOf(
        Setting(nameOfTheSetting = "Setting", label = "", id = 1),
        Setting(nameOfTheSetting = "Destination Preferences", label = "", id = 2),
        Setting(nameOfTheSetting = "Payment", label = "Under Development ...", id = 3),
        Setting(nameOfTheSetting = "Notifications", label = "", id = 4),
        Setting(nameOfTheSetting = "Help", label = "", id = 5),
        Setting(nameOfTheSetting = "About", label = "", id = 6),
        Setting(nameOfTheSetting = "Privacy Policy", label = "", id = 7),
        Setting(nameOfTheSetting = "Logout", label = "", id = 8)

    )

    enum class SettingNavigation(val id: Int) {
        Null(0),
        Setting(1),
        DestinationPreferences(2),
        Payment(3),
        Notifications(4),
        Help(5),
        About(6),
        PrivacyPolicy(7),
        Logout(8)
    }

    private var _profileUser = MutableLiveData<User>()
    val profileUser: LiveData<User>
        get() = _profileUser

    private var _settings = MutableLiveData<List<Setting>>()
    val settings: LiveData<List<Setting>>
        get() = _settings

    private val _navTo = MutableLiveData<SettingNavigation>()
    val navTo: LiveData<SettingNavigation>
        get() = _navTo


    init {
        _settings.value = localData
    }

    fun getAndUpdateUserInformation(vararg keyValue: String) {
        if (keyValue.size % 2 != 0) {
            throw IllegalArgumentException("keyValue must be even")
        } else {
            db.getAndUpdateUserInfo(*keyValue) {
                _profileUser.value = it
            }
        }
    }

    fun navigateTo(setting: Setting) {
        when (setting.id) {
            1 -> _navTo.value = SettingNavigation.Setting
            2 -> _navTo.value = SettingNavigation.DestinationPreferences
            3 -> _navTo.value = SettingNavigation.Payment
            4 -> _navTo.value = SettingNavigation.Notifications
            5 -> _navTo.value = SettingNavigation.Help
            6 -> _navTo.value = SettingNavigation.About
            7 -> _navTo.value = SettingNavigation.PrivacyPolicy
            8 -> _navTo.value = SettingNavigation.Logout
        }

    }

    fun navigateToComplete() {
        _navTo.value = SettingNavigation.Null
    }

}