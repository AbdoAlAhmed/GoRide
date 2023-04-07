package com.theideal.goride.ui.driver.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.FragmentHomeDriverBinding
import com.theideal.goride.model.HomeDriverFirebase
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
        val vieModelFactory = HomeDriverViewModelFactory(HomeDriverFirebase())
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

                }
                HomeDriverViewModel.HomeDriverServices.Error -> {

                }

            }
        }
            return binding.root


    }
}