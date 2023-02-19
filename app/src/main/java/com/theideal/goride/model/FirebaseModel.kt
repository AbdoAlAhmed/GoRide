package com.theideal.goride.model

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirebaseModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun createAccountAndSaveDataRider(rider: Rider): Task<Boolean> {
        val rideInfoRef = db.collection("users").document()
        return db.runTransaction { transaction ->
            transaction.set(rideInfoRef, rider)
            val authResult = auth.createUserWithEmailAndPassword(rider.email, rider.getPassword())
            if (authResult.isSuccessful) {
                val user = authResult.result?.user
                user?.let {
                    val userInfo = hashMapOf(
                        "id" to user.uid,
                        "userType" to "rider"
                    )
                    transaction.set(rideInfoRef, userInfo, SetOptions.merge())
                    return@runTransaction true
                } ?: return@runTransaction true
            } else {
                return@runTransaction false
            }
        }
    }

    fun createAccountAndSaveDataDriver(driver: Driver): Task<Boolean> {
        val driverInfoRef = db.collection("users").document()
        return db.runTransaction { transaction ->
            transaction.set(driverInfoRef, driver)
            val authResult = auth.createUserWithEmailAndPassword(driver.email, driver.getPassword())
            if (authResult.isSuccessful) {
                val user = authResult.result?.user
                user?.let {
                    val userInfo = hashMapOf(
                        "id" to user.uid,
                        "userType" to "driver"
                    )
                    transaction.set(driverInfoRef, userInfo, SetOptions.merge())
                    return@runTransaction true
                } ?: return@runTransaction true
            } else {
                return@runTransaction false
            }
        }
    }

    fun getUserData(id: String,callback:(String?)->Unit) {
        db.collection("users").document(id).get().addOnSuccessListener {
            callback(it.getString("userType"))
        }

    }

    fun sigInDriver(driver: Driver): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(driver.email, driver.getPassword())

    }

    fun sigInRider(rider: Rider): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(rider.email, rider.getPassword())
    }

    fun signOut() {
        auth.signOut()
    }

}