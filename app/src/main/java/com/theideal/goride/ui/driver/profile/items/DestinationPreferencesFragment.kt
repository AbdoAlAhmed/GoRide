package com.theideal.goride.ui.driver.profile.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.FragmentDestinationPreferencesBinding


class DestinationPreferencesFragment : Fragment() {
    private lateinit var viewModel: DestinationPreferenceViewModel
    private lateinit var binding: FragmentDestinationPreferencesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDestinationPreferencesBinding.inflate(inflater, container, false)
        val viewModelFactory = DestinationPreferenceViewModelFactory(
            DestinationPreferenceFirebase(),
            requireActivity().application
        )
        viewModel =
            ViewModelProvider(this, viewModelFactory)[(DestinationPreferenceViewModel::class.java)]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.rvTripsLine.adapter =
            DestinationPreferenceAdapter(DestinationPreferenceAdapter.OnClickListener {
                viewModel.addTripToList(it)
            })
        binding.rvTripsLine2.adapter =
            DestinationPreferenceAdapter(DestinationPreferenceAdapter.OnClickListener {

            })
        return binding.root
    }

}