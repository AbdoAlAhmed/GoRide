package com.theideal.goride.model

import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class ServicesDriverFirebase() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // change the data class fro card view data to something else
    fun getRideServicesServicesFragment(callback: (ArrayList<CardViewData>) -> Unit) {
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