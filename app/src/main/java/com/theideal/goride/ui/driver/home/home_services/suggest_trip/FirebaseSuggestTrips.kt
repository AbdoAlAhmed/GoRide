package com.theideal.goride.ui.driver.home.home_services.suggest_trip

import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.TripsLine
import timber.log.Timber

class FirebaseSuggestTrips() : FirebaseAuthModel() {
    private val db = FirebaseFirestore.getInstance()



    fun addTrips(trips: TripsLine, result: (Boolean) -> Unit) {
        db.collection("TripsLine").add(trips).addOnSuccessListener {
            db.collection("TripsLine").get().addOnSuccessListener { result ->
                result(!result.isEmpty)
            }
        }
    }

    fun getTrips(vararg keyValue: String,callback: (ArrayList<TripsLine>) -> Unit) {
        val list = ArrayList<TripsLine>()
        db.collection("TripsLine")
            .whereNotEqualTo(keyValue[0], keyValue[1])
            .get().addOnSuccessListener { result ->
            for (document in result) {
                val data = document.toObject(TripsLine::class.java)
                list.add(data)
                Timber.i(document.data.toString())
            }
            callback(list)
        }
    }

}