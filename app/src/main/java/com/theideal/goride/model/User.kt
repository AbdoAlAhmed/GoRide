package com.theideal.goride.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


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
    var userType: String = "rider"
): Parcelable {
    fun getPassword() = password
    fun setPassword(newPassword: String){
        this.password = newPassword
    }

}