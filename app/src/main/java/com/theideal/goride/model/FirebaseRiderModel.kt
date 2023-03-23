package com.theideal.goride.model

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
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


    fun requestFromAvailableTrips(trip: Trip) {
        val dbRef = db.collection("request-a-rides")
            .document("available-trips")
            .collection("rider-driver")
            .whereEqualTo("tripStatus", "available")
            .limit(1) // Only retrieve one available trip (if any)
        dbRef.get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.documents.isNotEmpty()) {
                    // Found an available trip
                    val availableTrip = querySnapshot.documents[0]
                    val tripId = availableTrip.id
                    // Update the available trip with the new trip information
                    // converted the trip to map
                    //  todo i'm here
                    availableTrip.reference.update(trip.riderId)
                        .addOnSuccessListener {
                            Timber.i("Requested ride from available trip with id: $tripId")
                        }
                        .addOnFailureListener { exception ->
                            Timber.e(
                                exception,
                                "Failed to request ride from available trip with id: $tripId"
                            )
                        }
                } else {
                    // No available trips found
                    Timber.i("No available trips found")
                }
            }
            .addOnFailureListener { exception ->
                Timber.e(exception, "Failed to retrieve available trips")
            }
    }


}



