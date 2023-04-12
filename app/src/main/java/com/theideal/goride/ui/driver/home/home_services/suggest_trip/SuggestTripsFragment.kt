package com.theideal.goride.ui.driver.home.home_services.suggest_trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.theideal.goride.databinding.FragmentSuggestTripBinding

class SuggestTripsFragment : Fragment() {

    private lateinit var viewModel: SuggestTripsViewModel
    private lateinit var binding: FragmentSuggestTripBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSuggestTripBinding.inflate(layoutInflater, null, false)
        viewModel = SuggestTripsViewModel()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }


}