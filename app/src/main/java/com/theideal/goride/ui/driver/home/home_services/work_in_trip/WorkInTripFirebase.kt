package com.theideal.goride.ui.driver.home.home_services.work_in_trip

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.TripsLine
import timber.log.Timber

class WorkInTripFirebase : FirebaseAuthModel() {
    private val db = FirebaseFirestore.getInstance()
    private val dbRef = db.collection("users").document("drivers")
        .collection("cars_info")
        .whereEqualTo("id", userId)

//    fun getTripData(callback: (ArrayList<TripsLine>) -> Unit) {
//        val tripsLineList = ArrayList<TripsLine>()
//        db.collection("trips_line")
//            .whereEqualTo("tripsLineStatus", "available")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    val tripsLine = document.toObject(TripsLine::class.java)
//                    tripsLineList.add(tripsLine)
//                    Timber.d("tripsLineList $tripsLineList")
//                }
//                callback(tripsLineList)
//            }
//            .addOnFailureListener { exception ->
//                println("Error getting documents: $exception")
//            }
//    }
//
//
//    fun addTrip(tripId: String) {
//        val tripIdList = ArrayList<String>()
//        dbRef.get().addOnSuccessListener { it ->
//            it.forEach { it1 ->
//                tripIdList.add(it1.get("workInTrip").toString())
//            }
//            for (i in tripIdList) {
//                if (i == tripId) {
//                    it.documents[0].reference.update("workInTrip", FieldValue.arrayRemove(tripId))
//                        .addOnSuccessListener {
//                            Timber.i("DocumentSnapshot successfully updated!")
//                        }
//                        .addOnFailureListener { e ->
//                            println("Error updating document $e")
//                        }
//                } else {
//                    it.documents[0].reference.update("workInTrip", FieldValue.arrayUnion(tripId))
//                        .addOnSuccessListener {
//                            Timber.i("DocumentSnapshot successfully updated!")
//                        }
//                        .addOnFailureListener { e ->
//                            println("Error updating document $e")
//                        }
//                }
//            }
//        }
//    }

}