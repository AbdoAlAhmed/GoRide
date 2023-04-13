package com.theideal.goride.ui.driver.home.home_services.suggest_trip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.ActivitySuggestTripsBinding

class SuggestTripsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuggestTripsBinding
    private lateinit var viewModel: SuggestTripsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestTripsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[SuggestTripsViewModel::class.java]
        binding.viewModel = viewModel
        supportActionBar!!.hide()
        setContentView(binding.root)


    }
}