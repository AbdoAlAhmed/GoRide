package com.theideal.goride.ui.driver.home

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.goride.model.Car
import com.theideal.goride.model.CardViewData
import com.theideal.goride.model.User
import timber.log.Timber

class HomeDriverFragmentFirebase() {
    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getRideServicesHome(callback: (ArrayList<CardViewData>) -> Unit) {
        val listServices = ArrayList<CardViewData>()
        database.collection("features").document("driver")
            .collection("home").get().addOnSuccessListener {
                for (document in it) {
                    val service = document.toObject(CardViewData::class.java)
                    Timber.i("Service: $service")
                    listServices.add(service)
                }
                callback(listServices)
            }
    }

    fun getCarInfo(callback: (Car) -> Unit) {
        val uid = auth.currentUser?.uid.toString()
        database.collection("drivers").document(uid).get().addOnSuccessListener {
            val driverInfo = it.toObject(Car::class.java)
            Timber.i("Driver Info: $driverInfo")
            callback(driverInfo!!)
        }
    }

    fun getDriverInfo(callback: (User) -> Unit) {
        val uid = auth.currentUser?.uid.toString()
        database.collection("users").document(uid).get().addOnSuccessListener {
            val driverInfo = it.toObject(User::class.java)
            Timber.i("Driver Info: $driverInfo")
            callback(driverInfo!!)
        }
    }

}