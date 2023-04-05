package com.theideal.goride.ui.driver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.theideal.goride.R
import com.theideal.goride.databinding.ActivityHomeDriverBinding

class DriverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeDriverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeDriverBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_driver) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomAppBarDriver.setupWithNavController(navController)

    }
}