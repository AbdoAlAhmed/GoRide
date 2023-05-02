package com.theideal.goride.model

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber

open class FirebaseAuthModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val dbRef = db.collection("users")
    private val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid
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


    suspend fun sigIn(user: User, callback: (User) -> Unit) {
        withContext(Dispatchers.IO) {
            val auth = auth.signInWithEmailAndPassword(user.email, user.getPassword()).await()
            auth.user.let { firebaseUser ->
                db.collectionGroup("users_info")
                    .whereEqualTo("id", firebaseUser!!.uid)
                    .whereNotEqualTo("firstName", "null")
                    .get().addOnSuccessListener {
                        for (document in it) {
                            Timber.i(document.data.toString())
                            val data = document.toObject(User::class.java)
                            callback(data)
                        }
                    }.addOnFailureListener {
                        Timber.i(it.toString())
                    }
            }
        }

    }


    fun getAndUpdateUserInfo(vararg keyValue: String, callback: (User) -> Unit) {
        if (userId != null) {
            Timber.i(userId)
            db.collectionGroup("users_info")
                .whereEqualTo("id", userId)
                .whereNotEqualTo("firstName", "null")
                .get().addOnSuccessListener {
                    for (document in it) {
                        Timber.i(document.data.toString())
                        if (keyValue.size % 2 == 0) {
                            for (i in keyValue.indices step 2) {
                                document.reference.update(keyValue[i], keyValue[i + 1])
                            }
                        }
                        val data = document.toObject(User::class.java)
                        callback(data)
                    }
                }.addOnFailureListener {
                    Timber.i(it.toString())
                }
        }
    }

    //todo  i'm here now
    fun getAndUpdateUserInfoWithUserData(
        user: User = User(),
        callback: (User) -> Unit,
    ) {
        if (userId != null) {
            Timber.i(userId)
            db.collectionGroup("users_info")
                .whereEqualTo("id", userId)
                .whereNotEqualTo("firstName", "null")
                .get().addOnSuccessListener {
                    for (document in it) {
                        if (user.firstName != "") {
                            document.reference.set(user, SetOptions.merge())
                        }
                        document.reference.set(user, SetOptions.merge())
                        val data = document.toObject(User::class.java)
                        callback(data)
                    }
                }.addOnFailureListener {
                    Timber.i(it.toString())
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
            db.collectionGroup("users_info")
                .whereEqualTo("id", auth.currentUser!!.uid)
                .whereNotEqualTo("firstName", "null")
                .get().addOnSuccessListener {
                    for (document in it) {
                        Timber.i(document.data.toString())
                        val data = document.toObject(User::class.java)
                        currentUser(true, data.userType)
                    }
                }.addOnFailureListener {
                    Timber.i(it.toString())
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