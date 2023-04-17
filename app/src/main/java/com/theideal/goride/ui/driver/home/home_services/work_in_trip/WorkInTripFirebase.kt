package com.theideal.goride.ui.driver.home.home_services.work_in_trip

import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.TripsLine

class WorkInTripFirebase : FirebaseAuthModel() {
    private val db = FirebaseFirestore.getInstance()

    fun getTripData(callback: (ArrayList<TripsLine>) -> Unit) {
        val tripsLineList = ArrayList<TripsLine>()
        db.collection("trips_line")
            .whereEqualTo("tripsLineStatus", "available")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val tripsLine = document.toObject(TripsLine::class.java)
                    tripsLineList.add(tripsLine)
                }
                callback(tripsLineList)
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }
}