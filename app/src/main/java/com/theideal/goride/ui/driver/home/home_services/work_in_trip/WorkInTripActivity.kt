package com.theideal.goride.ui.driver.home.home_services.work_in_trip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.R
import com.theideal.goride.databinding.ActivityTripBinding

class WorkInTripActivity : AppCompatActivity() {
    private lateinit var workInTripViewModel: WorkInTripViewModel
    private lateinit var binding: ActivityTripBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripBinding.inflate(layoutInflater)
        workInTripViewModel = ViewModelProvider(this)[WorkInTripViewModel::class.java]
        binding.viewModel = workInTripViewModel
        supportActionBar!!.hide()
        setContentView(binding.root)

    }
}