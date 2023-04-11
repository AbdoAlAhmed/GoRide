package com.theideal.goride.ui.driver.profile

import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.User

class ProfileDriverFirebase : FirebaseAuthModel() {

    fun getAndUpdateUserInformation(vararg keyValue: String, callback: (User) -> Unit) {
        if (keyValue.size % 2 != 0) {
            throw IllegalArgumentException("keyValue must be even")
        } else {
            getAndUpdateUserInfo(*keyValue) {
                callback(it)
            }
        }
    }
}