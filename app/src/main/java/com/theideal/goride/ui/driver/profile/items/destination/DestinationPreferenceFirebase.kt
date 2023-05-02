package com.theideal.goride.ui.driver.profile.items.destination

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.TripsLine
import timber.log.Timber

class DestinationPreferenceFirebase : FirebaseAuthModel() {
    private val db = FirebaseFirestore.getInstance()
    private val dbRef = db.collection("users").document("drivers")
        .collection("cars_info")
        .whereEqualTo("id", userId)

    fun getTripData(callback: (ArrayList<TripsLine>) -> Unit) {
        val tripsLineList = ArrayList<TripsLine>()
        db.collection("trips_line")
            .whereEqualTo("tripsLineStatus", "available")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val tripsLine = document.toObject(TripsLine::class.java)
                    tripsLineList.add(tripsLine)
                    Timber.d("tripsLineList $tripsLineList")
                }
                callback(tripsLineList)
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }


    fun addAndGetTrip(
        tripIdList: ArrayList<String> = arrayListOf(),
        callback: (ArrayList<String>) -> Unit
    ) {
        val tripsLineList = ArrayList<String>()
        dbRef.get().addOnSuccessListener { it ->
            it.forEach {
                val list = it.get("workInLines").toString()
                tripsLineList.add(list)
            }
            callback(tripsLineList)
            if (tripIdList.isNotEmpty()) {
                Timber.d("tripIdList is Not Empty: $tripIdList")
                it.documents[0].reference.set(
                    mapOf(
                        "workInLines" to tripIdList
                    ), SetOptions.merge()
                ).addOnSuccessListener {
                    Timber.d("WorkInLines added successfully -- 51 -- : $tripIdList")
                }.addOnFailureListener { e ->
                    Timber.d("WorkInLines add failed: $e")
                }
            }
        }
    }

}