package com.theideal.goride.model

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber

class FirebaseAuthModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val dbRef = db.collection("users")
    private val auth = FirebaseAuth.getInstance()
    private val storage = Firebase.storage.reference


    fun createAccountAndSaveDataRider(user: User) {
        auth.createUserWithEmailAndPassword(user.email, user.getPassword()).addOnSuccessListener {
            dbRef.document("riders").collection("users_info").document(it.user!!.uid).set(user)
            dbRef.document("riders").collection("users_info").document(it.user!!.uid)
                .update("id", it.user!!.uid)
            verifyEmail()
        }.addOnFailureListener {
            Timber.i(it.toString())
        }
    }


    fun createAccountAndSaveDataDriver(user: User, callback: (Boolean) -> Unit) {
        val auth = auth.createUserWithEmailAndPassword(user.email, user.getPassword())
        auth.addOnSuccessListener {
            dbRef.document("drivers").collection("users_info").document(it.user!!.uid).set(user)
            dbRef.document("drivers").collection("users_info").document(it.user!!.uid)
                .update("id", it.user!!.uid)
            verifyEmail()
            callback(true)
        }.addOnFailureListener {
            Timber.i(it.toString())
            callback(false)
        }

    }

    fun uploadCarInfo(car: Car, result: (Boolean) -> Unit) {

        dbRef.document("drivers").collection("cars_info").document(auth.currentUser!!.uid)
            .set(car).addOnSuccessListener {
                dbRef.document("drivers").collection("cars_info").document(auth.currentUser!!.uid)
                    .update("id", auth.currentUser!!.uid)
                result(true)
            }.addOnFailureListener {
                result(false)
            }
    }


    // todo  it's not working because col user doc drivers or riders
    suspend fun sigIn(user: User, callback: (User) -> Unit) {
        withContext(Dispatchers.IO) {
            val auth = auth.signInWithEmailAndPassword(user.email, user.getPassword()).await()
            auth.user.let { firebaseUser ->
                dbRef.document("drivers")
                    .collection("users_info")
                    .document(firebaseUser!!.uid)
                    .get().addOnSuccessListener {
                        if (it.exists()) {
                            val data = it.toObject(User::class.java)
                            callback(data!!)
                        } else {
                            dbRef.document("riders")
                                .collection("users_info")
                                .document(firebaseUser.uid)
                                .get().addOnSuccessListener {
                                    if (it.exists()) {
                                        val data = it.toObject(User::class.java)
                                        callback(data!!)
                                    }
                                }
                                .addOnFailureListener {
                                    Timber.i(it.toString())
                                }
                        }
                    }
                    .addOnSuccessListener {
                        Timber.i(it.toString())
                    }
            }
        }

    }

    // todo  i'm here
    fun getUser(callback: (User) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            dbRef.document(uid).get().addOnSuccessListener {
                val data = it.toObject(User::class.java)
                callback(data!!)
            }
        }
    }

    private fun verifyEmail() {
        auth.currentUser?.sendEmailVerification()!!
    }

    fun signOut() {
        auth.signOut()
    }

    // todo fixit
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

    fun uploadImage(uri: Uri, imageName: String, callback: (Boolean) -> Unit) {
        val user = auth.currentUser
        val storageRef =
            storage.child("users/${user!!.uid}/images/$imageName")
        storageRef.putFile(uri).addOnSuccessListener {
            Timber.i("Success")
            callback(true)
        }.addOnFailureListener {
            Timber.i(it.toString())
            callback(false)
        }

    }

    // some thing wrong with this function
    // todo fixit
    fun downloadImage(imageName: String): Uri {

        val user = auth.currentUser
        val storageRef =
            storage.child("users/${user!!.uid}/images/$imageName")
        val uri = storageRef.downloadUrl
        return uri.result!!
    }

    fun forgetPassword(user: User, callback: (Boolean) -> Unit) {
        auth.sendPasswordResetEmail(user.email).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }

    // todo fixit update password in profile
    fun updatePassword(newPassword: User, callback: (Boolean) -> Unit) {
        val user = auth.currentUser
        user!!.updatePassword(newPassword.getPassword()).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }
    // todo fixit

    fun updateEmail(newEmail: User, callback: (Boolean) -> Unit) {
        val user = auth.currentUser
        user!!.updateEmail(newEmail.email).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }
    // todo fixit
    fun updateProfile(user: User, callback: (Boolean) -> Unit) {
        val user = auth.currentUser
        dbRef.document(user!!.uid).set(user).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }
    // todo fixit
    fun updateProfileDriver(user: User, car: Car, callback: (Boolean) -> Unit) {
        val user = auth.currentUser
        dbRef.document(user!!.uid).set(car).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }


}