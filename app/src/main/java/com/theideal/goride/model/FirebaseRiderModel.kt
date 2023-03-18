package com.theideal.goride.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import timber.log.Timber

class FirebaseRiderModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()


    fun getRideServicesHome(callback: (ArrayList<CardViewData>) -> Unit) {
        val listServices = ArrayList<CardViewData>()
        db.collection("features").document("rider")
            .collection("home").document("services")
            .collection("main").get().addOnSuccessListener {
                if (it != null) {
                    for (document in it) {
                        val services = document.toObject(CardViewData::class.java)
                        listServices.add(services)
                    }
                    callback(listServices)
                } else {
                    Timber.d("No such document")
                }
            }
            .addOnFailureListener {
                Timber.d(it, "get failed with ")
            }

    }


    fun getAvailableTrips(callback: (ArrayList<Trip>) -> Unit) {
        val listTrips = ArrayList<Trip>()
        db.collection("features").document("rider")
            .collection("home").document("services")
            .collection("availableTrips").get().addOnSuccessListener {
                if (it != null) {
                    for (document in it) {
                        val trips = document.toObject(Trip::class.java)
                        Timber.d(trips.toString())
                        listTrips.add(trips)
                    }
                    callback(listTrips)
                } else {
                    Timber.d("No such document")
                }
            }.addOnFailureListener { exception ->
                Timber.d(exception, "get failed with ")
            }
    }

    fun getDriver(callback: (ArrayList<DriverStatus>) -> Unit) {
        val listOfDriver = ArrayList<DriverStatus>()
        db.collection("driver")
            .whereNotEqualTo("status", "NotAvailable")
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    val data = document.toObject(DriverStatus::class.java)
                    Timber.i(data.toString())
                    listOfDriver.add(data)
                    callback(listOfDriver)
                }
            }
            .addOnFailureListener { exception ->
                Timber.d(exception, "get failed with ")

            }
    }


}



