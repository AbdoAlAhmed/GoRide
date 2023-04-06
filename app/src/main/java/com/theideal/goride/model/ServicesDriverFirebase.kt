package com.theideal.goride.model

import com.google.firebase.firestore.FirebaseFirestore

class ServicesDriverFirebase(private val db: FirebaseFirestore) {

        // change the data class fro card view data to something else
        fun getRideServicesServicesFragment(callback: (ArrayList<CardViewData>) -> Unit) {
            val listServices = ArrayList<CardViewData>()
            db.collection("features").document("driver")
                .collection("services_fragment").document("services")
                .collection("main").get().addOnSuccessListener {
                    for (document in it) {
                        val service = document.toObject(CardViewData::class.java)
                        listServices.add(service)
                    }
                    callback(listServices)
                }
        }
}