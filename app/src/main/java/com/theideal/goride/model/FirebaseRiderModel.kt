package com.theideal.goride.model

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
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

    fun getDriver(vararg keyValue: String, callback: (ArrayList<User>) -> Unit) {
        val listOfDriver = ArrayList<User>()
        var query = db.collection("users").whereEqualTo("userType", "Driver")
        if (keyValue.size % 2 == 0) {
            for (i in keyValue.indices step 2) {
                Timber.i(keyValue[i])
                query = query.whereEqualTo(keyValue[i], keyValue[i + 1])
            }
        }
        query.get()
            .addOnSuccessListener {
                for (document in it) {
                    val data = document.toObject(User::class.java)
                    Timber.i(data.toString())
                    listOfDriver.add(data)
                }
                callback(listOfDriver)
            }
            .addOnFailureListener { exception ->
                Timber.d(exception, "get failed with ")
            }
    }

    fun requestFromAvailableTrips(tripId: String, trip: Trip) {
        db.collection("request-a-rides")
            .document("available-trips")
            .collection("rider-driver")
            .document(tripId)
            .set(trip)
            .addOnSuccessListener {
                Timber.i(it.toString())
            }
            .addOnFailureListener {
                Timber.i(it.toString())
            }
    }


}



