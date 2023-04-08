package com.theideal.goride.ui.auth.sigin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.User
import com.theideal.goride.ui.auth.AuthenticationViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class SignInViewModel(private val firebaseAuthModel: FirebaseAuthModel) :
    AuthenticationViewModel(firebaseAuthModel) {

    private val _isSignInRider2 = MutableLiveData<User>()
    val isSignInRider2: LiveData<User>
        get() = _isSignInRider2


    fun signIn2(user: User) {
        viewModelScope.launch {
            firebaseAuthModel.sigIn(user) {
                Timber.i("signIn2: $it")
                _isSignInRider2.value = it
            }
        }

    }
}