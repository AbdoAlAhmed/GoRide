package com.theideal.goride.ui.driver.profile.items.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.User

class AccountInformationViewModel(val db: AccountInformationFirebase) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user
    private val _updateDialog = MutableLiveData<Boolean>()
    val updateDialog: LiveData<Boolean>
        get() = _updateDialog


    init {

        db.getAndUpdateUserInfo {
            _user.value = it

        }
        _updateDialog.value = false
    }

    fun updateDialog() {
        _updateDialog.value = true
    }

    fun updateDialogDone() {
        _updateDialog.value = false
    }


    fun updateUserInfo() {
        db.getAndUpdateUserInfo(
            "firstName",
            user.value?.firstName.toString(),
            "phone",
            user.value?.phone.toString(),
            "password",
            user.value?.getPassword().toString()
        ) {
            _user.value = it
        }
    }

}