package com.theideal.goride.ui.driver.services

import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.goride.model.CardViewData
import timber.log.Timber

class ServicesDriverFirebase() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // change the data class fro card view data to something else
    fun getDriverServices_(callback: (ArrayList<CardViewData>) -> Unit) {
        val listServices = ArrayList<CardViewData>()
        db.collection("features").document("driver")
            .collection("services").get().addOnSuccessListener {
                Timber.d("Success")
                for (document in it) {
                    val service = document.toObject(CardViewData::class.java)
                    Timber.d("Service: $service")
                    listServices.add(service)
                }
                callback(listServices)
            }
    }
}