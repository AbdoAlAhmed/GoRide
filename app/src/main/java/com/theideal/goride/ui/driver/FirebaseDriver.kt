package com.theideal.goride.ui.driver

import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.User

class FirebaseDriver() : FirebaseAuthModel() {
    private val db = FirebaseFirestore.getInstance()

    fun getDriverInfo(callback: (User)-> Unit){
    }

}