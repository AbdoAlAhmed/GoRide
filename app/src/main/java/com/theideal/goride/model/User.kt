package com.theideal.goride.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import timber.log.Timber


@Parcelize
data class User(
    var id: String = "",
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
    fun setPassword(newPassword: String) {
        this.password = newPassword
    }

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