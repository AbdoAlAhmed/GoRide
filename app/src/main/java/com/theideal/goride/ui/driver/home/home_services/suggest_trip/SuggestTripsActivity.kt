package com.theideal.goride.ui.driver.home.home_services.suggest_trip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.ActivitySuggestTripsBinding
import com.theideal.goride.ui.driver.DriverHomeActivityFirebase
import com.theideal.goride.ui.driver.DriverViewModel
import com.theideal.goride.ui.driver.DriverViewModelFactory

class SuggestTripsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuggestTripsBinding
    private lateinit var viewModel: SuggestTripsViewModel
    private lateinit var viewModel2: DriverViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestTripsBinding.inflate(layoutInflater)
        val viewModelFactory = SuggestTripsViewModelFactory(FirebaseSuggestTrips())
        viewModel = ViewModelProvider(this, viewModelFactory)[SuggestTripsViewModel::class.java]
        val viewModelFactory2 = DriverViewModelFactory(DriverHomeActivityFirebase())
        viewModel2 = ViewModelProvider(this, viewModelFactory2)[(DriverViewModel::class.java)]
        viewModel2.setDriverAvailable()
        binding.viewModel = viewModel
        binding.viewModelDriverHome = viewModel2
        binding.lifecycleOwner = this
        setContentView(binding.root)


    }
}