package com.theideal.goride.ui.rider

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.theideal.goride.databinding.FragmentHomeRiderBinding
import com.theideal.goride.model.CardViewData
import com.theideal.goride.ui.rider.home.services.availableTrip.AvailableTripsActivity
import com.theideal.goride.ui.rider.home.services.request.RiderRequestActivity
import timber.log.Timber


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
        viewModel.navTo.observe(viewLifecycleOwner) {
            when (it) {

                RiderViewModel.HomeServicesFragment.Request -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            RiderRequestActivity::class.java
                        )
                    )
                    viewModel.doneNavTo()
                }
                RiderViewModel.HomeServicesFragment.Available -> {
                    startActivity(
                        Intent(
                            requireContext(),
                            AvailableTripsActivity::class.java
                        )
                    )
                    viewModel.doneNavTo()
                }

                else -> {
                    Timber.i("else")
                }
            }
        }


        return binding.root
    }

}