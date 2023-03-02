package com.theideal.goride.ui.rider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.GoogleMap
import com.theideal.goride.databinding.FragmentHomeRiderBinding
import com.theideal.goride.model.CardViewData
import com.theideal.goride.ui.rider.adatper.CardViewAdapter
import com.theideal.goride.viewmodel.rider.RiderViewModel


class HomeRiderFragment : Fragment() {
    private lateinit var viewModel: RiderViewModel

    private lateinit var googleMap: GoogleMap
    private lateinit var binding: FragmentHomeRiderBinding
    private lateinit var cardViewAdapter: CardViewAdapter
    private val card: CardViewData? = null
    private var list = ArrayList<CardViewData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeRiderBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[RiderViewModel::class.java]

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.getRideServicesHome()
        binding.rideServices.adapter = CardViewAdapter(CardViewAdapter.OnClick {
            viewModel.navTo(it)
        })
        viewModel.navToAvailableTrip.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(HomeRiderFragmentDirections.actionHomeRiderFragmentToAvailableTripFragment())
                viewModel.doneNavToAvailableTrip()
            }
        }
        viewModel.navToRideRequest.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(HomeRiderFragmentDirections.actionHomeRiderFragmentToRideRequestFragment())
                viewModel.doneNavToRideRequest()
            }
        }
        viewModel.navToTripSuggestions.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(HomeRiderFragmentDirections.actionHomeRiderFragmentToTripSuggestionsFragment())
                viewModel.doneNavToTripSuggestions()
            }
        }
        viewModel.navToFragmentError.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(HomeRiderFragmentDirections.actionHomeRiderFragmentToErrorFragment())
                viewModel.doneNavToErrorFragment()
            }
        }

        return binding.root
    }

}