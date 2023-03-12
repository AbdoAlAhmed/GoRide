package com.theideal.goride.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.theideal.goride.R
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.ui.driver.DriverActivity
import com.theideal.goride.ui.rider.RiderActivity
import timber.log.Timber

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var viewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        val viewModelFactory = AuthenticationViewModelFactory(FirebaseAuthModel())
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[AuthenticationViewModel::class.java]
        viewModel.isSignIn()
        viewModel.isSignInRider.observe(this) {
            Timber.i(it.toString())
            if (it) {
                runOnUiThread {
                    startActivity(Intent(this, RiderActivity::class.java))
                    finish()
                }
            }
        }
        viewModel.isSignInDriver.observe(this) {
            if (it) {
                runOnUiThread {
                    startActivity(Intent(this, DriverActivity::class.java))
                    finish()
                }
            }
        }

    }
}