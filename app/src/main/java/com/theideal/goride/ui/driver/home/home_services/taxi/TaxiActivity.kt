package com.theideal.goride.ui.driver.home.home_services.taxi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.ActivityTaxiBinding

class TaxiActivity : AppCompatActivity() {
    private lateinit var taxiViewModel: TaxiViewModel
    private lateinit var binding: ActivityTaxiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaxiBinding.inflate(layoutInflater)
        taxiViewModel = ViewModelProvider(this)[TaxiViewModel::class.java]
        binding.viewModel = taxiViewModel
        setContentView(binding.root)
    }
}