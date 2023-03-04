package com.theideal.goride.ui.rider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.theideal.goride.R
import com.theideal.goride.databinding.ActivityHomeRiderBinding

class RiderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeRiderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeRiderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomAppBar.setupWithNavController(navController)

    }

}