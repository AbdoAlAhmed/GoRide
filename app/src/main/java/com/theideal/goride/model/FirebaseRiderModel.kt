package com.theideal.goride.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRiderModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()


    fun getRideServicesHome(callback: (ArrayList<CardViewData>) -> Unit) {
        val listServices = ArrayList<CardViewData>()
        val db = db.collection("features").document("rider")
            .collection("home").document("services")
            .collection("main").addSnapshotListener { value, error ->
                if (error != null) {
                    Log.i("TAG", error.message.toString())
                }
                if (value != null) {
                    for (document in value) {

                        val cardViewData = document.toObject(CardViewData::class.java)
                        listServices.add(cardViewData!!)
                        callback(listServices)
                    }
                }
            }


    }

}
