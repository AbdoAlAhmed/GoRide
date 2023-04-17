package com.theideal.goride.ui.driver.home.home_services.work_in_trip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.ActivityTripBinding
import com.theideal.goride.ui.driver.DriverHomeActivityFirebase
import com.theideal.goride.ui.driver.DriverViewModel
import com.theideal.goride.ui.driver.DriverViewModelFactory

class WorkInTripActivity : AppCompatActivity() {
    private lateinit var workInTripViewModel: WorkInTripViewModel
    private lateinit var homeDriverViewModel: DriverViewModel
    private lateinit var binding: ActivityTripBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripBinding.inflate(layoutInflater)
        workInTripViewModel = ViewModelProvider(this)[WorkInTripViewModel::class.java]
        val viewModelFactory = DriverViewModelFactory(DriverHomeActivityFirebase())
        homeDriverViewModel =
            ViewModelProvider(this, viewModelFactory)[(DriverViewModel::class.java)]
        homeDriverViewModel.setDriverAvailable()
        binding.viewModel = workInTripViewModel
        binding.viewModelDriver = homeDriverViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

    }
}