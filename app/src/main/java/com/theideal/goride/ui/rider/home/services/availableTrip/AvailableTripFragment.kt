package com.theideal.goride.ui.rider.home.services.availableTrip

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.CardListOfDriverBinding
import com.theideal.goride.databinding.DialogPickATripBinding
import com.theideal.goride.databinding.FragmentAvailableTripBinding
import com.theideal.goride.model.Trip
import kotlin.properties.Delegates


class AvailableTripFragment : Fragment() {
    private lateinit var binding: FragmentAvailableTripBinding
    private lateinit var viewModel: AvailableTripsViewModel
    private var trip = Trip()


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
        viewModel.initializeAvailableTrips()
        binding.rvAvailableTrips.adapter = AvailableTripAdapter(AvailableTripAdapter.OnClick {
            trip = it
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
        view.trip = trip
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
        view.availableDriver = viewModel
        view.rcAvailableDrivers.adapter = AvailableDriverAdapter(AvailableDriverAdapter.OnClick {

        })
        dialogCreate.show()
    }


    /*i think to solve this issue use tipsLine instead of trips so i got all the trips line issue (1)
     issue (2) how to get the driver info remove driver status class
    todo issue (3) how to get the rider info to get the rider info we are going to get from data

    after fixing this get rider and driver and trips line info and put it in request a ride
     */
}