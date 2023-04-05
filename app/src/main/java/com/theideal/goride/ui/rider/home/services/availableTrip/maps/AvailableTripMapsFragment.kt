package com.theideal.goride.ui.rider.home.services.availableTrip.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.FragmentAvailableTripMapsBinding
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.FirebaseRiderModel
import com.theideal.goride.model.User
import com.theideal.goride.ui.rider.home.services.availableTrip.AvailableTripsViewModel
import com.theideal.goride.ui.rider.home.services.availableTrip.AvailableTripsViewModelFactory


class AvailableTripMapsFragment : Fragment() {
    private lateinit var binding: FragmentAvailableTripMapsBinding
    private lateinit var viewModelAvailableTrips: AvailableTripsViewModel
    private var user = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAvailableTripMapsBinding.inflate(inflater, container, false)
        val viewModelFactory = AvailableTripsViewModelFactory(
            FirebaseRiderModel(),
            FirebaseAuthModel()
        )
        viewModelAvailableTrips = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[AvailableTripsViewModel::class.java]
        binding.viewModel = viewModelAvailableTrips
        binding.lifecycleOwner = this
        viewModelAvailableTrips.getOrRequestDriver()




        return binding.root
    }


}