package com.theideal.goride.ui.driver.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.User

class ProfileDriverViewModel(val db: ProfileDriverFirebase) : ViewModel() {
    private var _profileUser = MutableLiveData<User>()
    val profileUser: MutableLiveData<User>
        get() = _profileUser


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