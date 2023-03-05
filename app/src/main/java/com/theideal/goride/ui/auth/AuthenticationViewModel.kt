package com.theideal.goride.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.User
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val firebaseAuthModel: FirebaseAuthModel) : ViewModel() {


    // sign in
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


    // nav
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

    // progress and snackBar
    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean>
        get() = _progressBar

    private val _snackBar = MutableLiveData<Boolean>()
    val snackBar: LiveData<Boolean>
        get() = _snackBar

    // validate
    val emailHelper = MutableLiveData<String>()
    val passwordHelper = MutableLiveData<String>()


    init {
        _isSignInDriver.value = false
        _navToForgetPassword.value = false
        _navToSignUpPage2Driver.value = false
        _navToSignUp.value = false
        _progressBar.value = false
        _signWithGoogle.value = false
        _showDialog.value = false
        _isSignUpRider.value = false
    }

    // validate


    fun createAnAccount(user: User) {
        viewModelScope.launch {
            firebaseAuthModel.createAccountAndSaveData(user)
        }
    }

    fun signIn(user: User) {
        _progressBar.value = true
        viewModelScope.launch {
            try {

                firebaseAuthModel.sigIn(user) {
                    when (it) {
                        "rider" -> {
                            _isSignInRider.value = true
                            _progressBar.value = false
                        }
                        "driver" -> {
                            _isSignInDriver.value = true
                            _progressBar.value = false
                        }
                        else -> {
                            _isSignInRider.value = false
                            _isSignInDriver.value = false
                            _progressBar.value = false
                        }
                    }
                }
            } catch (ex: FirebaseAuthInvalidCredentialsException) {
                _progressBar.value = false
                passwordHelper.value = "password is wrong or invalid email"
                emailHelper.value = "invalid email or password is wrong"
            } catch (ex: FirebaseAuthInvalidUserException) {
                _progressBar.value = false
                emailHelper.value = "user not found or disable"
            } catch (ex: FirebaseAuthException) {
                _progressBar.value = false
                emailHelper.value = "Error"
                passwordHelper.value = "Error"
            } catch (ex: FirebaseAuthEmailException) {
                _progressBar.value = false
                emailHelper.value = "Error"
                passwordHelper.value = "Error"
            } catch (ex: FirebaseException) {
                _snackBar.value = true
            }

        }
    }


    fun signOut() {
        firebaseAuthModel.signOut()
    }

    fun showDialog() {
        _showDialog.value = true
    }

    fun navToSignUpPage2Driver() {
        _navToSignUpPage2Driver.value = true
    }

    fun doneNavToSignUpPage2Driver() {
        _navToSignUpPage2Driver.value = false
    }

    fun doneNavToSignUp() {
        _navToSignUp.value = false
    }

    fun navToSignUP() {
        _navToSignUp.value = true
    }

    fun navToForgetPassword() {
        _navToForgetPassword.value = true
    }

    fun doneNavToForgetPassword() {
        _navToForgetPassword.value = false
    }

    fun signUpRiderComplete(user: User) {
        firebaseAuthModel.createAccountAndSaveDataRider(user)
        _isSignUpRider.value = true
    }

    fun isSignIn() {
        firebaseAuthModel.checkUserAuth { checkUser, userType ->

            if (checkUser) {
                when (userType) {
                    "rider" -> _isSignInRider.value = true
                    "driver" -> _isSignInDriver.value = true
                    else -> {
                        _isSignInRider.value = false
                        _isSignInDriver.value = false
                    }
                }
            } else {
                _isSignInRider.value = false
                _isSignInDriver.value = false
            }

        }
    }
}