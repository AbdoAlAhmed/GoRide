package com.theideal.goride.ui.splachscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.R
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.ui.auth.AuthenticationActivity
import com.theideal.goride.ui.auth.AuthenticationViewModel
import com.theideal.goride.ui.auth.AuthenticationViewModelFactory
import com.theideal.goride.ui.driver.DriverActivity
import com.theideal.goride.ui.rider.RiderActivity
import timber.log.Timber
import kotlin.properties.Delegates

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var viewModel: AuthenticationViewModel
    private  var isSignIn: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach_screen)
        val viewModelFactory = AuthenticationViewModelFactory(FirebaseAuthModel())
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[AuthenticationViewModel::class.java]
        viewModel.isSignIn()
        // create a new HandlerThread

        viewModel.isSignInRider.observe(this) {
            Timber.i(it.toString())
            if (it) {
                isSignIn = "Rider"
            }
        }
        viewModel.isSignInDriver.observe(this) {
            if (it) {
                Timber.i(it.toString())
                isSignIn = "Driver"
            }
        }

        val handlerThread = HandlerThread("SplashScreenHandlerThread")
        handlerThread.start()

        val handler = Handler(handlerThread.looper)

        handler.postDelayed({
            // Start your app main activity
            when (isSignIn) {
                "Rider" -> {
                    startActivity(Intent(this, RiderActivity::class.java))

                }
                "Driver" -> {
                    startActivity(Intent(this, DriverActivity::class.java))
                }
                else -> {
                    startActivity(Intent(this, AuthenticationActivity::class.java))
                }
            }
            finish()
        }, 5010)


    }
}