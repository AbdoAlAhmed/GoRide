package com.theideal.goride.model

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.theideal.goride.utility.sendNotification
import timber.log.Timber

 class FirebaseRiderModel {
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

    fun getOrRequestDriver(
        vararg keyValue: String,
        callback: (User, Car) -> Unit
    ) {
        val dbRef = db.collection("users")
        var query = db.collection("drivers")
            .whereEqualTo("status", "available")
        if (keyValue.size % 2 == 0) {
            for (i in keyValue.indices step 2) {
                Timber.i(keyValue[i])
                query = query.whereEqualTo(keyValue[i], keyValue[i + 1])
            }
        }
        query.get()
            .addOnSuccessListener {
                val data = it.documents[0]
                if (it.documents.isNotEmpty()) {
                    val carInfo = data.toObject(Car::class.java)
                    if (carInfo != null) {

                        dbRef.whereEqualTo("id", carInfo.id)
                            .get().addOnSuccessListener {
                                if (it != null) {
                                    for (document in it) {
                                        val driverInfo = document.toObject(User::class.java)
                                        callback(driverInfo, carInfo)
                                    }
                                } else {
                                    Timber.d("No such document")
                                }
                            }
                    }
                }else{
                    Timber.d("request driver")
                    // Send a request to the driver

                }

            }
            .addOnFailureListener { exception ->
                Timber.d(exception, "get driver failed")
            }
    }

    fun setAvailableTrip(trip: Trip) {
        val dbRef = db.collection("request-a-rides")
            .document("available-trips")
            .collection("rider-driver")
            .whereEqualTo("tripStatus", "available")
            .limit(1) // Only retrieve one available trip (if any)
            .get()
        dbRef.addOnSuccessListener {
            dbRef.result?.documents?.forEach { document ->
                val tripId = document.id
                document.reference.set(trip)
                    .addOnSuccessListener {
                        Timber.i("Requested ride from available trip with id: $tripId")
                    }
                    .addOnFailureListener { exception ->
                        Timber.e(
                            exception,
                            "Failed to request ride from available trip with id: $tripId"
                        )
                    }
            }
        }
        dbRef.addOnFailureListener { exception ->
            Timber.e(exception, "Failed to add available")
        }

    }

    fun addRiderIdToAvailableTrip(riderId: String) {
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
                    availableTrip.reference.update(FieldPath.of("riderId"), riderId)
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

    /*
    1- create a new trip if there is no available trip with the same riderId, startDestination, and endDestination
    2- get the available trip and update it with the new trip information (riderId, tripStatus,increment the number of riders)
    3 - when your getting the available trip check those conditions
        - check if the number of riders is equal to the number of seats
         - check if the trip status is available

     */
    fun getOrCreateTrip(trip: Trip) {
        val dbRef = db.collection("request-a-rides")
            .document("available-trips")
            .collection("rider-driver")

        dbRef.whereIn("tripStatus", listOf("available", "requested"))
            .whereEqualTo("startDestination", trip.startDestination)
            .whereEqualTo("endDestination", trip.endDestination)
            .orderBy("numberOfRiders", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.documents.isEmpty()) {
                    // create a new trip
                    dbRef.add(trip)
                        .addOnSuccessListener {
                            dbRef.document(it.id).update(
                                "tripId", it.id, "tripStatus", "requested", "numberOfRiders", 1
                            )
                            // todo send notification to the driver that a new trip is created

                            Timber.i("not found available trip so created a new trip")
                        }
                        .addOnFailureListener { exception ->
                            Timber.e(
                                exception,
                                "Failed to create a new trip"
                            )
                        }
                } else {
                    // found an available trip
                    val availableTrip = querySnapshot.documents[0]
                    val data = availableTrip.toObject(Trip::class.java)
                    val numberOfRiders = data?.numberOfRiders?.plus(1)
                    val tripId = availableTrip.id
                    // check if the riderId is already in the array
                    /* todo there is a problem here if the same rider try to request the same trip twice it will be added to the array twice
                     todo because the []
                    */
                    val result = data!!.riderId.find { it == trip.riderId.toString() }
                    if (result != null) {
                        Timber.i("you already requested this trip $result")
                    } else {
                        // Update the available trip with the new trip information
                        Timber.i("${data.riderId + "and" + trip.riderId}")
                        availableTrip.reference.update(
                            "numberOfRiders", numberOfRiders,
                            "riderId", FieldValue.arrayUnion(trip.riderId.toString())
                        )
                            .addOnSuccessListener {
                                Timber.i("Requested ride from available trip with id: $tripId")
                            }
                            .addOnFailureListener { exception ->
                                Timber.e(
                                    exception,
                                    "Failed to request ride from available trip with id: $tripId"
                                )
                            }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Timber.e(exception, "Failed to retrieve available trips")
            }
    }
}



