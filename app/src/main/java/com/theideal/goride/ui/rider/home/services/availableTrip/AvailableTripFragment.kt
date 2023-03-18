package com.theideal.goride.ui.rider.home.services.availableTrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.theideal.goride.databinding.FragmentAvailableTripBinding
import com.theideal.goride.model.GeoFencing
import kotlin.properties.Delegates


class AvailableTripFragment : Fragment() {
    private lateinit var binding: FragmentAvailableTripBinding
    private lateinit var viewModel: AvailableTripsViewModel
    private var tripId by Delegates.notNull<Int>()
    private lateinit var geoFencing: GeoFencing
    private lateinit var geofencingClient: GeofencingClient
    private val requestCodeGeofence = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAvailableTripBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[AvailableTripsViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        geofencingClient = LocationServices.getGeofencingClient(requireActivity())
        viewModel.initializeAvailableTrips()
        binding.rvAvailableTrips.adapter = AvailableTripAdapter(AvailableTripAdapter.OnClick {
            tripId = it.id
            requestFromAvailableTrip()
        })

        return binding.root
    }

    private fun requestFromAvailableTrip() {
        viewModel.getAvailableDriver()
    }


}