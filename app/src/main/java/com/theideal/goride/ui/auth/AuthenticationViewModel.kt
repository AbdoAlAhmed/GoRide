package com.theideal.goride.ui.auth

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestoreException
import com.theideal.goride.model.Car
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.User
import kotlinx.coroutines.launch

open class AuthenticationViewModel(private val firebaseAuthModel: FirebaseAuthModel) : ViewModel() {

    val array = arrayOf("Rider", "Driver", "Admin")

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

    private val _isSignUpDriverCompleted = MutableLiveData<Boolean>()
    val isSignUpDriverCompleted: LiveData<Boolean>
        get() = _isSignUpDriverCompleted


    private val _signWithGoogle = MutableLiveData<Boolean>()
    val signWithGoogle: LiveData<Boolean>
        get() = _signWithGoogle

    private val _deleteUserAccountDialog = MutableLiveData<Boolean>()
    val deleteUserAccountDialog: LiveData<Boolean>
        get() = _deleteUserAccountDialog


    // nav
    private val _navToCarInfoFragment = MutableLiveData<Boolean>()
    val navToCarInfoFragment: LiveData<Boolean>
        get() = _navToCarInfoFragment

    private val _navToSignUpPage2Driver = MutableLiveData<Boolean>()
    val navToSignUpPage2Driver: LiveData<Boolean>
        get() = _navToSignUpPage2Driver

    private val _navToSignUp = MutableLiveData<Boolean>()
    val navToSignUp: LiveData<Boolean>
        get() = _navToSignUp

    private val _navToForgetPassword = MutableLiveData<Boolean>()
    val navToForgetPassword: LiveData<Boolean>
        get() = _navToForgetPassword

    // request permission
    private val _requestPermission = MutableLiveData<Boolean>()
    val requestPermission: LiveData<Boolean>
        get() = _requestPermission

    // progress and snackBar
    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean>
        get() = _progressBar

    private val _snackBar = MutableLiveData<String>()
    val snackBar: LiveData<String>
        get() = _snackBar

    // validate
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val emailHelper = MutableLiveData<String>()
    val passwordHelper = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val age = MutableLiveData<String>()
    val userType = MutableLiveData<String>()

    val diskNumberHelper = MutableLiveData<String>()

    private val _imageUpload = MutableLiveData<String>()
    val imageUpload: LiveData<String>
        get() = _imageUpload

    private val _imageUploadCompleted = MutableLiveData<Boolean>()
    val imageUploadCompleted: LiveData<Boolean>
        get() = _imageUploadCompleted


    init {
        _isSignInDriver.value = false
        _navToForgetPassword.value = false
        _navToSignUpPage2Driver.value = false
        _navToSignUp.value = false
        _progressBar.value = false
        _signWithGoogle.value = false
        _isSignUpRider.value = false

    }

    // validate

    fun validateEmailAndPassword(user: User): Boolean {
        if (user.email.isEmpty()) {
            emailHelper.value = "email is required"
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            emailHelper.value = "invalid email"
            return false
        } else {
            emailHelper.value = ""
        }

        if (user.getPassword().isEmpty()) {
            passwordHelper.value = "password is required"
            return false
        } else if (user.getPassword().length < 6) {
            passwordHelper.value = "password must be at least 6 characters"
            return false
        } else {
            passwordHelper.value = ""
        }
        return true
    }

    fun validateEmail(user: User): Boolean {
        if (user.email.isEmpty()) {
            emailHelper.value = "email is required"
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            emailHelper.value = "invalid email"
            return false
        } else {
            emailHelper.value = ""
        }
        return true
    }

    fun validateText(user: User): Boolean {
        if (user.firstName.isEmpty()) {
            firstName.value = "first name is required"
            return false
        } else {
            firstName.value = ""
        }

        if (user.lastName.isEmpty()) {
            lastName.value = "last name is required"
            return false
        } else {
            lastName.value = ""
        }

        if (user.email.isEmpty()) {
            emailHelper.value = "email is required"
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            emailHelper.value = "invalid email"
            return false
        } else {
            emailHelper.value = ""
        }

        if (user.getPassword().isEmpty()) {
            passwordHelper.value = "password is required"
            return false
        } else if (user.getPassword().length < 6) {
            passwordHelper.value = "password must be at least 6 characters"
            return false
        } else if (!user.getPassword().any { it.isLetter() }) {
            passwordHelper.value = "password must contain at least one letter"
            return false

        } else {
            passwordHelper.value = ""
        }

        if (user.phone.isEmpty()) {
            phoneNumber.value = "phone number is required"
            return false
        } else if (!user.phone.startsWith("01") || user.phone.length != 11) {
            phoneNumber.value = "invalid phone number"
            return false
        } else {
            phoneNumber.value = ""
        }

        if (user.age.isEmpty()) {
            age.value = "age is required"
            return false
        } else if (user.age.toInt() < 10) {
            age.value = "you must be at least 10 years old"
            return false
        } else if (user.age.toInt() > 100) {
            age.value = "invalid age"
            return false
        } else {
            age.value = ""
        }

        if (user.userType.isEmpty()) {
            userType.value = "user type is required"
            return false
        } else {
            userType.value = ""
        }

        return true
    }

    // permission
    fun checkPermission(context: Context, permission: String) {
        _requestPermission.value = ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(activity: Activity, permission: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, permission, requestCode)
    }

    fun setPermissionGranted() {
        _requestPermission.value = true
    }

    fun setPermissionDenied() {
        _requestPermission.value = false
    }

    // sign in and sign up
    fun singUpDriver(user: User): Boolean {
        try {
            firebaseAuthModel.createAccountAndSaveDataDriver(user) {
                if (it) {
                    _navToSignUpPage2Driver.value = true
                } else {
                    _snackBar.value = "error"
                }
            }
        } catch (e: FirebaseAuthWeakPasswordException) {
            passwordHelper.value = "password is weak"
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            emailHelper.value = "Invalid email"
        } catch (e: FirebaseAuthUserCollisionException) {
            emailHelper.value = "User already exists"
        } catch (e: FirebaseAuthException) {
            _snackBar.value = "Error"
        } catch (e: FirebaseNetworkException) {
            _snackBar.value = "No internet connection"
        } catch (e: FirebaseFirestoreException) {
            _snackBar.value = "Error"
        } catch (e: Exception) {
            _snackBar.value = "Error"
        }

        return true

    }


    fun navToCarInfoFragmentComplete() {
        _navToCarInfoFragment.value = false
    }

    fun signUpDriverCompletedDoneNav() {
        _isSignUpDriverCompleted.value = false
    }

    fun uploadCarInfo(car: Car) {
        try {
            firebaseAuthModel.uploadCarInfo(car) {
                _isSignUpDriverCompleted.value = it
            }
        } catch (e: FirebaseFirestoreException) {
            _snackBar.value = "Error"
        } catch (e: Exception) {
            _snackBar.value = "Error"
        }
    }

    fun navToCarInfoFragment() {
        _navToCarInfoFragment.value = true
    }


    fun checkUserType(user: User) {
        when (user.userType) {
            "Rider" -> {
                signUpRiderComplete(user)

            }
            "Driver" -> {
                singUpDriver(user)

            }
            else -> {
                _snackBar.value = "Rider or Driver"
            }
        }
    }

    fun signIn(user: User) {
        _progressBar.value = true
        viewModelScope.launch {
            try {

                firebaseAuthModel.sigIn(user) {
                    when (it.userType) {
                        "Rider" -> {
                            _isSignInRider.value = true
                            _progressBar.value = false
                        }
                        "Driver" -> {
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
                _snackBar.value = "Error"
            } catch (ex: Exception) {
                _progressBar.value = false
                emailHelper.value = "Error"
                passwordHelper.value = "Error"
            }

        }
    }

    fun signOut() {
        firebaseAuthModel.signOut()
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

    fun doneSnackBar() {
        _snackBar.value = ""
    }

    fun setSnackBar(message: String) {
        _snackBar.value = message
    }

    fun signUpRiderComplete(user: User) {
        try {
            firebaseAuthModel.createAccountAndSaveDataRider(user)
        } catch (e: FirebaseAuthWeakPasswordException) {
            passwordHelper.value = "password is weak"
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            emailHelper.value = "Invalid email"
        } catch (e: FirebaseAuthUserCollisionException) {
            emailHelper.value = "User already exists"
        } catch (e: FirebaseAuthException) {
            _snackBar.value = "Error"
        } catch (e: FirebaseNetworkException) {
            _snackBar.value = "No internet connection"
        } catch (e: FirebaseFirestoreException) {
            _snackBar.value = "Error"
        } catch (e: Exception) {
            _snackBar.value = "Error"
        }
        _isSignUpRider.value = true
    }

    fun isSignIn() {
        firebaseAuthModel.checkUserAuth { checkUser, userType ->
            if (checkUser) {
                when (userType) {
                    "Rider" -> _isSignInRider.value = true
                    "Driver" -> _isSignInDriver.value = true
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

    fun forgetPassword(user: User) {
        firebaseAuthModel.forgetPassword(user) {
            _snackBar.value =
                if (it) "Email sent successfully" else "Error"
        }
    }

    fun uploadImage(uri: Uri, imageName: String) {
        firebaseAuthModel.uploadImage(uri, imageName) {
            _imageUploadCompleted.value = it
        }
    }

    fun downloadImage(imageName: String): Uri? {

        return firebaseAuthModel.downloadImage(imageName)

    }


    fun startUploadImage(imageName: String): Boolean {
        _imageUpload.value = imageName
        return true
    }

    fun logoutBoth() {
        firebaseAuthModel.signOut()
        _isSignInDriver.value = false
        _isSignInRider.value = false
    }

    fun deleteUserAccountDialog() {
        _deleteUserAccountDialog.value = true
    }

    fun doneDeleteUserAccountDialog() {
        _deleteUserAccountDialog.value = false
    }

    fun deleteAccount() {
        viewModelScope.launch {
            firebaseAuthModel.deleteAccount()
        }
    }

}