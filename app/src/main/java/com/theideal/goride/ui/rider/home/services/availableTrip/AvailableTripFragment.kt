package com.theideal.goride.ui.rider.home.services.availableTrip

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.theideal.goride.databinding.CardRequestFromAvailableTripBinding
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
                trip = it
                viewModel.user.value?.let { user ->
                    if (!trip.riderId.contains(user.id)) {
                        trip.riderId.add(user.id)
                    }
                }
                confirmRequested()
            }
        )
        viewModel.senNotification(requireContext(), trip)
        binding.rvAvailableTrips.adapter = adapter
        viewModel.navToAvailableTrip.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(AvailableTripFragmentDirections.actionAvailableTripFragmentToAvailableTripMapsFragment())
                viewModel.navToAvailableTripCompleteMaps()
            }
        }

        return binding.root
    }

    private fun confirmRequested() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val dialogCreated = dialogBuilder.create()
        val view = CardRequestFromAvailableTripBinding.inflate(layoutInflater, null, false)
        dialogCreated.setView(view.root)
        view.confirmRequest.setOnClickListener {
            viewModel.getOrCreateTrip(trip)
            viewModel.navToAvailableTripMaps()
            dialogCreated.dismiss()
        }
        dialogCreated.show()

    }


}