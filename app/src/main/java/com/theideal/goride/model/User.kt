package com.theideal.goride.model

data class User(
    var id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    private var password: String = "",
    var phone: String = "",
    var age: String = "",
    var gender: Gender = Gender.Male,
    var userType: String = ""
){
    fun getPassword() = password
    fun setPassword(newPassword: String){
        this.password = newPassword
    }

}