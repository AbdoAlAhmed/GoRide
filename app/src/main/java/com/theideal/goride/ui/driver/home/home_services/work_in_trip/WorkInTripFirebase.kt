package com.theideal.goride.ui.driver.home.home_services.work_in_trip

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.goride.model.CheckBoxForChooseTrip
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.TripsLine

class WorkInTripFirebase : FirebaseAuthModel() {
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
                }
                callback(tripsLineList)
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }

    // todo i'm here 

    private fun setDriverWorkInTrip(): LiveData<List<CheckBoxForChooseTrip>> {
        val listOfCheckTrip = MutableLiveData<List<CheckBoxForChooseTrip>>()
        dbRef.get()
            .addOnSuccessListener {
                it.forEach { document ->
                    val checkBox = document.toObject(CheckBoxForChooseTrip::class.java)
                    listOfCheckTrip.postValue(listOf(checkBox))
                }
            }
            .addOnFailureListener { e ->
                println("Error updating document $e")
            }
        return listOfCheckTrip
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun addTrip(vararg trip: String) {
        val trip = setDriverWorkInTrip()
        dbRef.get().addOnSuccessListener {
            it.documents[0].reference.update("workInLine", FieldValue.arrayUnion(trip))
        }
    }

    fun removeTrip(vararg trip: String) {
        val trip = setDriverWorkInTrip()
        dbRef.get().addOnSuccessListener {
            it.documents[0].reference.update("workInLine", FieldValue.arrayRemove(trip))
        }
    }

}