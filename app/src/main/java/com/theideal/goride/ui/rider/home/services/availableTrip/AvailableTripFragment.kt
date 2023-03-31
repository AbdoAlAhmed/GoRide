package com.theideal.goride.ui.rider.home.services.availableTrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.FragmentAvailableTripBinding
import com.theideal.goride.model.Trip


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
        viewModel.getUser()
        /* todo
        (1) userId adding it.id doesn't work == done
        (2) If there another rider wanna ask for a trip he will create new trip so how to make sure he will be added to the same trip
        if it's not full yet , i suggest add status
        (3) date doesn't work
        (4) for tonay How to add now his time ( a green dot )
        (5) Available Trips will be add by the admin
        (6) In Available trip "trip Status" it's going to change by the driver
        (7) In Available trip should being automatic created like this example
         - Admin create an available trip
         - status will be "waiting for driver"
         - driver id will be empty
         - when driver accept the trip the status will be "waiting for riders" and driver id will be added
         - add check if the trip is full or not from going to driver id and check his car capacity
         - when rider accept and there is no more seat  the trip  status will be "trip started"
         - How to know if the trip is started ?
         - How to know if the trip is canceled ?
         - How to know if the trip is finished ?
         - when the trip is finished the status will be "trip finished"
         - then it's going to be deleted from the available trips
         - How to know if the trip is deleted ?
         - then create a new trip after checking if the last trip that relevant is finished


         */
        binding.rvAvailableTrips.adapter = AvailableTripAdapter(AvailableTripAdapter.OnClick {
            trip = it
            viewModel.user.value?.let { it ->
                trip.riderId.add(it.id)
            }
            viewModel.getAvailableDriver()

        })

        return binding.root
    }


}