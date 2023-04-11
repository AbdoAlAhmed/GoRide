package com.theideal.goride.ui.driver

import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.User

class FirebaseDriver() : FirebaseAuthModel() {
    private val db = FirebaseFirestore.getInstance()


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