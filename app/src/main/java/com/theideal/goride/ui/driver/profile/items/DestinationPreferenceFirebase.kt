package com.theideal.goride.ui.driver.profile.items

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


    fun addAndGetTrip(vararg tripIdList: List<String>, callback: (ArrayList<TripsLine>) -> Unit) {
        val tripsLineList = ArrayList<TripsLine>()
        dbRef.get().addOnSuccessListener { it ->
            it.forEach {
                val it = it.toObject(TripsLine::class.java)
                tripsLineList.add(it)
            }
            callback(tripsLineList)
            if (tripIdList.isNotEmpty()) {
                it.documents[0].reference.set(
                    hashMapOf(
                        "WorkInLines" to tripIdList
                    ), SetOptions.merge()
                )
            }

        }
    }

}