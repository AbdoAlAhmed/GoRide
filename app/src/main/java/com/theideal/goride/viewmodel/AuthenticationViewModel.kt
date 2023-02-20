package com.theideal.goride.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theideal.goride.model.FirebaseModel
import com.theideal.goride.model.User
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val firebaseModel: FirebaseModel) : ViewModel() {

    private val _isSignInDriver = MutableLiveData<Boolean>()
    val isSignInDriver: LiveData<Boolean>
        get() = _isSignInDriver

    private val _isSignInRider = MutableLiveData<Boolean>()
    val isSignInRider: LiveData<Boolean>
        get() = _isSignInRider

    private val _isSignUpRider = MutableLiveData<Boolean>()
    val isSignUpRider: LiveData<Boolean>
        get() = _isSignUpRider

    private val _signWithGoogle = MutableLiveData<Boolean>()
    val signWithGoogle: LiveData<Boolean>
        get() = _signWithGoogle

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean>
        get() = _showDialog

    private val _navToSignUpPage2Driver = MutableLiveData<Boolean>()
    val navToSignUpPage2Driver: LiveData<Boolean>
        get() = _navToSignUpPage2Driver

    private val _navToSignUp = MutableLiveData<Boolean>()
    val navToSignUp: LiveData<Boolean>
        get() = _navToSignUp

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
        _navToSignUpPage2Driver.value = false
        _navToSignUp.value = false
        _progressBar.value = false
        _signWithGoogle.value = false
        _showDialog.value = false
        _isSignUpRider.value = false
    }


    fun createAnAccount(user: User) {
        viewModelScope.launch {
        firebaseModel.createAccountAndSaveData(user)
        }
    }

//    fun createAnAccountRider(rider: Rider) {
//        val authResult = firebaseModel.createAccountAndSaveDataRider(rider)
//    }

    fun signIn(user: User) {
        val driver = firebaseModel.sigIn(user)
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

    fun showDialog(){
        _showDialog.value = true
    }

    fun navToSignUpPage2Driver() {
        _navToSignUpPage2Driver.value = true
    }
    fun doneNavToSignUpPage2Driver(){
        _navToSignUpPage2Driver.value = false
    }
    fun doneNavToSignUp(){
        _navToSignUp.value = false
    }

    fun navToSignUP() {
        _navToSignUp.value = true
    }

    fun navToForgetPassword() {
        _navToForgetPassword.value = true
    }

    fun signUpRiderComplete(user: User){
        val authResult = firebaseModel.createAccountAndSaveDataRider(user)
        _isSignUpRider.value = true
    }
}