package com.theideal.goride.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.R
import com.theideal.goride.databinding.FragmentSigninBinding
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.ui.rider.RiderActivity
import com.theideal.goride.viewmodel.auth.AuthenticationViewModel
import com.theideal.goride.viewmodel.auth.AuthenticationViewModelFactory

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

        viewModel.isSignInRider.observe(this) {
            if (it) {
                startActivity(Intent(this, RiderActivity::class.java))
                finish()

            }
        }

    }
}