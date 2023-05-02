package com.theideal.goride.ui.driver.profile.items.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.User

class AccountInformationViewModel(val db: AccountInformationFirebase) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user


    init {
        db.getAndUpdateUserInfoWithUserData {
            _user.value = it
        }
    }

    fun getAndUpdateUserInfoWithUserData(u: User) {

    }

}