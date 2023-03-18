package com.theideal.goride.ui.rider.home.services.availableTrip

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.theideal.goride.R
import com.theideal.goride.databinding.CardAvailableTripsBinding
import com.theideal.goride.databinding.CardListOfDriverBinding
import com.theideal.goride.databinding.DialogPickATripBinding
import com.theideal.goride.databinding.FragmentAvailableTripBinding
import com.theideal.goride.model.GeoFencing
import com.theideal.goride.model.TripsLine
import kotlin.properties.Delegates


class AvailableTripFragment : Fragment() {
    private lateinit var binding: FragmentAvailableTripBinding
    private lateinit var viewModel: AvailableTripsViewModel
    private var tripsLine = TripsLine()
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
            firstStepToRequestTripDialog()
            viewModel.getAvailableDriver()

        })

        return binding.root
    }


    private fun firstStepToRequestTripDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogBuilder.create()
        val view = DialogPickATripBinding.inflate(LayoutInflater.from(context), null, false)
        dialogCreate.setView(view.root)
        view.tripsLine = tripsLine
        view.btnRequestFromDialog.setOnClickListener {
            lastStepToRequestTripDialog()
            dialogCreate.dismiss()
        }
        dialogCreate.show()
    }

    private fun lastStepToRequestTripDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogBuilder.create()
        val view = CardListOfDriverBinding.inflate(LayoutInflater.from(context), null)
        dialogCreate.setView(view.root)
        // todo make the onClickListener to move to fragment to track the driver>>
        view.rcAvailableDrivers.adapter = AvailableDriverAdapter()
        view.availableDriver = viewModel
        dialogCreate.show()
    }


}