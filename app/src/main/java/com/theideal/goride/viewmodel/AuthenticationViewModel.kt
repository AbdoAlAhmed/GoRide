package com.theideal.goride.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.Driver
import com.theideal.goride.model.FirebaseModel
import com.theideal.goride.model.Rider

class AuthenticationViewModel(private val firebaseModel: FirebaseModel) : ViewModel() {

    private val _isSignInDriver = MutableLiveData<Boolean>()
    val isSignInDriver: LiveData<Boolean>
        get() = _isSignInDriver

    private val _isSignInRider = MutableLiveData<Boolean>()
    val isSignInRider: LiveData<Boolean>
        get() = _isSignInRider

    private val _navToSignUpDriver = MutableLiveData<Boolean>()
    val navToSignUpDriver: LiveData<Boolean>
        get() = _navToSignUpDriver

    private val _navToSignUpRider = MutableLiveData<Boolean>()
    val navToSignUpRider: LiveData<Boolean>
        get() = _navToSignUpRider

    private val _navToForgetPassword = MutableLiveData<Boolean>()
    val navToForgetPassword: LiveData<Boolean>
        get() = _navToForgetPassword

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean>
        get() = _progressBar


    init {
        _isSignInDriver.value = false
        _isSignInRider.value = false
        _navToForgetPassword.value = false
        _navToSignUpDriver.value = false
        _navToSignUpRider.value = false
        _progressBar.value = false
    }


    fun createAnAccountDriver(driver: Driver) {
        firebaseModel.createAccountAndSaveDataDriver(driver)
    }

    fun createAnAccountRider(rider: Rider) {
        firebaseModel.createAccountAndSaveDataRider(rider)
    }

    fun signIn(driver: Driver) {
        val driver = firebaseModel.sigInDriver(driver)
        firebaseModel.getUserData(driver.result.user!!.uid){
            if (it == "driver"){
                _isSignInDriver.value = true
                _isSignInRider.value = false
            }else{
                _isSignInDriver.value = false
                _isSignInRider.value = true
            }
        }

    }

    fun signOut() {
        firebaseModel.signOut()
    }

    fun navToSignUpDriver() {
        _navToSignUpDriver.value = true
    }

    fun navToSignUPRider() {
        _navToSignUpRider.value = true
    }

    fun navToForgetPassword() {
        _navToForgetPassword.value = true
    }
}