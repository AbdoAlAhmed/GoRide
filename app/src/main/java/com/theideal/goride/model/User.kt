package com.theideal.goride.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import timber.log.Timber
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


@Parcelize
data class User(
    var id: String = "",
    var tokenId: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    private var password: String = "",
    var phone: String = "",
    var age: String = "",
    var gender: Gender = Gender.Male,
    var userType: String = "",
    var status: String = "",
    var profileImage: String = "",

) : Parcelable {
    fun getPassword() = password

    fun setPassword(password: String) {
        this.password = password
    }

    // encrypt password before saving to database
//    fun encryptPassword(password: String, secretKey: SecretKey): ByteArray {
//        val cipher = Cipher.getInstance("AES")
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
//        return cipher.doFinal(password.toByteArray(Charsets.UTF_8))
//    }
//
//    fun decryptPassword(encryptedPassword: ByteArray, secretKey: SecretKey): String {
//        val cipher = Cipher.getInstance("AES")
//        cipher.init(Cipher.DECRYPT_MODE, secretKey)
//        val decryptedBytes = cipher.doFinal(encryptedPassword)
//        return String(decryptedBytes, Charsets.UTF_8)
//    }
//    // example
//    val password2 = "mysecretpassword"
//    val secretKey = SecretKeySpec("mysecretpassword".toByteArray(), "AES")


    private val updateStatus: Boolean
        get() = userType.equals("Rider")

    fun updateStatus(etStatus: String) {
        status = if (updateStatus) {
            etStatus

        } else {
            ""
        }
        Timber.i("user status == $status")
    }

}