package com.theideal.goride.ui.driver.home.home_services.work_in_trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.theideal.goride.model.CheckBoxForChooseTrip
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.TripsLine
import timber.log.Timber

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
                    Timber.d("tripsLineList $tripsLineList")
                }
                callback(tripsLineList)
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }


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

    fun addTrip(vararg tripid: String) {

        dbRef.get().addOnSuccessListener {
            Timber.i(userId)
//            it.documents[0].reference.update("workInLine", FieldValue.arrayUnion(tripid))
        }
    }

    fun removeTrip(tripid: String) {
        val list = listOf(tripid)
        val map = hashMapOf(
            "workInLine" to tripid
        )
        try {
            dbRef.get().addOnSuccessListener {
                it.documents[0].reference.set(map, SetOptions.merge())
            }

        } catch (e: FirebaseException) {
            println("Error updating document $e")
        } catch (e: Exception) {
            println("Error updating document $e")
        }
    }

}