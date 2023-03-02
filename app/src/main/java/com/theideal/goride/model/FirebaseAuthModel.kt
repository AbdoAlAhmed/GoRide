package com.theideal.goride.model

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class FirebaseAuthModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun createAccountAndSaveDataRider(user: User): Task<Boolean> {
        val rideInfoRef = db.collection("users").document()
        return db.runTransaction { transaction ->
            transaction.set(rideInfoRef, user)
            val authResult = auth.createUserWithEmailAndPassword(user.email, user.getPassword())
            if (authResult.isSuccessful) {
                val user = authResult.result?.user
                user?.let {
                    val userInfo = hashMapOf(
                        user.uid to "id",
                        "rider" to "userType"
                    )
                    transaction.set(rideInfoRef, userInfo, SetOptions.merge())
                    return@runTransaction true
                } ?: return@runTransaction true
            } else {
                return@runTransaction false
            }
        }
    }

    suspend fun createAccountAndSaveData(user: User) {
        val authResult = auth.createUserWithEmailAndPassword(user.email, user.getPassword()).await()
        val userR  = authResult.user!!
        db.collection("users").document(userR.uid).set(user)
        verifyEmail()

    }

    fun getUserData(id: String, callback: (String?) -> Unit) {
        db.collection("users").document(id).get().addOnSuccessListener {
            callback(it.getString("userType"))
        }

    }

    fun sigIn(user: User): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(user.email, user.getPassword())

    }

    fun verifyEmail() {
        auth.currentUser?.sendEmailVerification()!!
    }

    fun signOut() {
        auth.signOut()
    }

}