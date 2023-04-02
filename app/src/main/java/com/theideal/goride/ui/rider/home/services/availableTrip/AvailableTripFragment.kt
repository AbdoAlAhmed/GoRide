package com.theideal.goride.ui.rider.home.services.availableTrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.FragmentAvailableTripBinding
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.FirebaseRiderModel
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
        val viewModelFactory = AvailableTripsViewModelFactory(
            FirebaseRiderModel(),
            FirebaseAuthModel()
        )
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[AvailableTripsViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.initializeAvailableTrips()
        viewModel.getUser()



        val adapter = AvailableTripAdapter(
            AvailableTripAdapter.OnClick {
                viewModel.user.value?.let { user ->
                    if (!trip.riderId.contains(user.id)) {
                        trip.riderId.add(user.id)
                        viewModel.getOrCreateTrip(trip)
                    }
                }
            }
        )
        binding.rvAvailableTrips.adapter = adapter

        return binding.root
    }


}