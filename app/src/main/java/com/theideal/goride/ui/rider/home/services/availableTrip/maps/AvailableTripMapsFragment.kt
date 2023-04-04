package com.theideal.goride.ui.rider.home.services.availableTrip.maps

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.theideal.goride.databinding.FragmentAvailableTripMapsBinding
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.FirebaseRiderModel
import com.theideal.goride.ui.rider.home.services.availableTrip.AvailableTripsViewModel
import com.theideal.goride.ui.rider.home.services.availableTrip.AvailableTripsViewModelFactory


class AvailableTripMapsFragment : Fragment() {
    private lateinit var binding: FragmentAvailableTripMapsBinding
    private lateinit var viewModelAvailableTrips: AvailableTripsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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



        return binding.root
    }


}