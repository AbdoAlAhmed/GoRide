package com.theideal.goride.model

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber

class FirebaseAuthModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val dbRef = db.collection("users")


    fun createAccountAndSaveDataRider(user: User) {
        auth.createUserWithEmailAndPassword(user.email, user.getPassword()).addOnSuccessListener {
            dbRef.document(it.user!!.uid).set(user)
            verifyEmail()
        }.addOnFailureListener {
            Timber.i(it.toString())
        }
    }


     fun createAccountAndSaveDataDriver(user: User, driver: Driver) {
        auth.createUserWithEmailAndPassword(user.email, user.getPassword()).addOnSuccessListener {
            dbRef.document(it.user!!.uid).set(driver)
            verifyEmail()
        }.addOnFailureListener {
            Timber.i(it.toString())
        }

    }

    suspend fun getUserData(uid: String, callback: (String?) -> Unit) {
        withContext(Dispatchers.IO) {
            db.collection("users").document(uid).get().addOnSuccessListener {
                val userType = it.getString("userType")
                callback(userType)
            }.addOnFailureListener {
                callback(it.toString())
            }
        }

    }

    suspend fun sigIn(user: User, callback: (String?) -> Unit) {
        withContext(Dispatchers.IO) {

            val auth = auth.signInWithEmailAndPassword(user.email, user.getPassword()).await()

            auth.user.let {
                dbRef.document(it!!.uid).get().addOnSuccessListener { it ->
                    val data = it.data?.get("userType")
                    Timber.i(data.toString())
                    callback(data.toString())

                }
            }
        }

    }

    private fun verifyEmail() {
        auth.currentUser?.sendEmailVerification()!!
    }

    fun signOut() {
        auth.signOut()
    }

    fun checkUserAuth(currentUser: (Boolean, String) -> Unit) {
        if (auth.currentUser != null) {
            dbRef.document(auth.currentUser!!.uid).get().addOnSuccessListener {
                val data = it.data?.get("userType")
                currentUser(true, data.toString())
            }
        } else {
            currentUser(false, "null")
        }
    }


}