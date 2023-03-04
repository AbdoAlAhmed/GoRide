package com.theideal.goride.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseAuthModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun createAccountAndSaveDataRider(user: User) {
        val rideInfoRef = db.collection("users").document()


        db.runTransaction { transaction ->
            transaction.set(rideInfoRef, user)
            val authResult =
                auth.createUserWithEmailAndPassword(user.email, user.getPassword())
            if (authResult.isSuccessful) {
                transaction.set(rideInfoRef, user)
            }
        }
    }

    suspend fun createAccountAndSaveData(user: User) {
        val authResult = auth.createUserWithEmailAndPassword(user.email, user.getPassword()).await()
        val userR = authResult.user!!
        db.collection("users").document(userR.uid).set(user)
        verifyEmail()

    }

    suspend fun getUserData(callback: (String?) -> Unit) {
        withContext(Dispatchers.IO) {
            db.collection("users").document().get().addOnSuccessListener {
                val cast = it.toObject(User::class.java)
                callback(cast?.userType)
            }
        }

    }

    suspend fun sigIn(user: User): AuthResult? {
        val result = auth.signInWithEmailAndPassword(user.email, user.getPassword()).await()
        Log.i("AuthenticationViewModel", result.toString())
        return result
    }

    private fun verifyEmail() {
        auth.currentUser?.sendEmailVerification()!!
    }

    fun signOut() {
        auth.signOut()
    }

    fun checkUserAuth(currentUser: (Boolean) -> Unit) {
        if (auth.currentUser != null) {
            currentUser(true)
        } else {
            currentUser(false)
        }
    }

}