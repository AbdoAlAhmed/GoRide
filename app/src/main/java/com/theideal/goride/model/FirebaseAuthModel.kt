package com.theideal.goride.model

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber

class FirebaseAuthModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val dbRef = db.collection("users")
    private val auth = FirebaseAuth.getInstance()
    private val store = Firebase.firestore


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

    fun uploadImage(uri: Uri, callback: (Boolean) -> Unit) {
        val user = auth.currentUser
        val storageRef =
            store.collection("users").document(user!!.uid)
                .collection("images").document("profile")
        storageRef.set(uri).addOnSuccessListener {
            Timber.i("Success")
            callback(true)
        }.addOnFailureListener {
            Timber.i(it.toString())
            callback(false)
        }

    }

    fun forgetPassword(user: User, callback: (Boolean) -> Unit) {
        auth.sendPasswordResetEmail(user.email).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }

    fun updatePassword(newPassword: User, callback: (Boolean) -> Unit) {
        val user = auth.currentUser
        user!!.updatePassword(newPassword.getPassword()).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }

    fun updateEmail(newEmail: User, callback: (Boolean) -> Unit) {
        val user = auth.currentUser
        user!!.updateEmail(newEmail.email).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }

    fun updateProfile(user: User, callback: (Boolean) -> Unit) {
        val user = auth.currentUser
        dbRef.document(user!!.uid).set(user).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }

    fun updateProfileDriver(user: User, driver: Driver, callback: (Boolean) -> Unit) {
        val user = auth.currentUser
        dbRef.document(user!!.uid).set(driver).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }




}