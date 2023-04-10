package com.theideal.goride.ui.driver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.theideal.goride.R
import com.theideal.goride.databinding.ActivityHomeDriverBinding

class DriverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeDriverBinding
    private lateinit var viewModel: DriverViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeDriverBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        val viewModelFactory = DriverViewModelFactory(FirebaseDriver())
        viewModel = ViewModelProvider(this, viewModelFactory)[(DriverViewModel::class.java)]
        viewModel.setDriverAvailable()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_driver) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomAppBarDriver.setupWithNavController(navController)

    }
}