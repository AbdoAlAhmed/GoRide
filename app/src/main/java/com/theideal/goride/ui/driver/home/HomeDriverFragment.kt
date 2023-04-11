package com.theideal.goride.ui.driver.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.FragmentHomeDriverBinding
import com.theideal.goride.ui.driver.home.home_services.work_in_specific_trip.WorkInSpecificTripActivity
import com.theideal.goride.ui.rider.CardViewAdapter

class HomeDriverFragment : Fragment() {
    private lateinit var binding: FragmentHomeDriverBinding
    private lateinit var viewModel: HomeDriverViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeDriverBinding.inflate(layoutInflater, null, false)
        val vieModelFactory = HomeDriverViewModelFactory(HomeDriverFragmentFirebase())
        viewModel =
            ViewModelProvider(requireActivity(), vieModelFactory)[HomeDriverViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getDriverServices()

        binding.driverServices.adapter = CardViewAdapter(CardViewAdapter.OnClick {
            viewModel.navTo(it)
        })
        viewModel.navTo.observe(viewLifecycleOwner) {
            when (it) {
                HomeDriverViewModel.HomeDriverServices.WorkInATaxi -> {

                }
                HomeDriverViewModel.HomeDriverServices.Suggest -> {

                }
                HomeDriverViewModel.HomeDriverServices.WorkInASpecificTrip -> {
                    startActivity(Intent(requireContext(), WorkInSpecificTripActivity::class.java))
                    viewModel.doneNavigating()

                }
                HomeDriverViewModel.HomeDriverServices.Error -> {

                }
                else -> {

                }

            }
        }
        return binding.root


    }
}