package com.theideal.goride.ui.driver.home.home_services.suggest_trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.FragmentSuggestTripBinding
import com.theideal.goride.ui.driver.home.HomeDriverFragmentFirebase
import com.theideal.goride.ui.driver.home.HomeDriverViewModel
import com.theideal.goride.ui.driver.home.HomeDriverViewModelFactory

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
        val viewModelFactory = SuggestTripsViewModelFactory(FirebaseSuggestTrips())
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[SuggestTripsViewModel::class.java]


        binding.lifecycleOwner = this
        return binding.root
    }


}