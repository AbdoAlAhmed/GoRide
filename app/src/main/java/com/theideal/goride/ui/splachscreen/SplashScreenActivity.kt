package com.theideal.goride.ui.splachscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.R
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.ui.auth.AuthenticationActivity
import com.theideal.goride.ui.rider.RiderActivity
import com.theideal.goride.viewmodel.auth.AuthenticationViewModel
import com.theideal.goride.viewmodel.auth.AuthenticationViewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach_screen)

        Handler().postDelayed(Runnable { //This method will be executed once the timer is over
            // Start your app main activity
            startActivity(Intent(this, AuthenticationActivity::class.java))

            // close this activity
            finish()
        }, 5010)

    }
}