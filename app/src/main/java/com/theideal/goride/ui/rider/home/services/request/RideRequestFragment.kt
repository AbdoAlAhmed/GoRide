package com.theideal.goride.ui.rider.home.services.request

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.theideal.goride.R
import com.theideal.goride.databinding.FragmentRideRequestBinding
import timber.log.Timber


class RideRequestFragment : Fragment(), OnMapReadyCallback {
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


        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)


        binding.tvStartDestination.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                viewModel.getQuery(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        viewModel.getAutoCompletePrediction(
            viewModel.query.toString()
        ).observe(viewLifecycleOwner) {
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, it)
            binding.tvStartDestination.setAdapter(adapter)
        }

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

}