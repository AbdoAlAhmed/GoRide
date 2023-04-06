package com.theideal.goride.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class HomeDriverFirebase(private val database: FirebaseFirestore, private val auth: FirebaseAuth) {


    fun getRideServicesHome(callback: (ArrayList<CardViewData>) -> Unit) {
        val listServices = ArrayList<CardViewData>()
        database.collection("features").document("driver")
            .collection("home").document("services")
            .collection("main").get().addOnSuccessListener {
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