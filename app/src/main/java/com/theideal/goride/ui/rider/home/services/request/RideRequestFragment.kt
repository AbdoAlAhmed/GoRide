package com.theideal.goride.ui.rider.home.services.request

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.theideal.goride.R
import com.theideal.goride.api_Key
import com.theideal.goride.databinding.FragmentRideRequestBinding
import timber.log.Timber
import java.util.*


class RideRequestFragment : Fragment(), PlaceSelectionListener, OnMapReadyCallback {
    private lateinit var binding: FragmentRideRequestBinding
    private lateinit var viewModel: RiderRequestViewModel
    private lateinit var googleMap: GoogleMap
    private val REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRideRequestBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[RiderRequestViewModel::class.java]




        viewModel.checkPermission(
            requireContext().applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        viewModel.permissionGranted.observe(viewLifecycleOwner) {
            if (it) {
                Timber.i("permission Granted")
            } else {
                viewModel.requestPermission(requireActivity(), permissions, REQUEST_CODE)
            }
        }

        try {

        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment_auto_complete) as AutocompleteSupportFragment

        autocompleteFragment.setOnPlaceSelectedListener(this)
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )
        }catch (ex:Exception){
            Timber.i(ex.message.toString())
        }

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)



        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        setMapStyle(map)
        googleMap.isMyLocationEnabled = true
    }

    private fun setMapStyle(googleMap: GoogleMap) {
        try {
            val result = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map)
            )
            if (!result) {
                Timber.i("some Thing happen")
            }

        } catch (ex: Exception) {
            Timber.i(ex.message.toString())
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode != null && requestCode == REQUEST_CODE) {
            viewModel.setPermissionGranted()
        } else {
            viewModel.setPermissionDenied()
        }
    }

    override fun onPlaceSelected(p0: Place) {
        Timber.i(p0.name.toString())
    }

    override fun onError(p0: Status) {
        Timber.i(p0.statusMessage.toString())
    }

}