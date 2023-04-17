package com.theideal.goride.ui.driver.home.home_services.work_in_trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.theideal.goride.databinding.FragmentWorkInTripBinding

class WorkInTripFragment : Fragment() {
    private lateinit var binding: FragmentWorkInTripBinding
    private lateinit var viewModel: WorkInTripFragmentViewModel
    private lateinit var viewModelFactory: WorkInTripFragmentViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWorkInTripBinding.inflate(inflater, container, false)
        viewModelFactory =
            WorkInTripFragmentViewModelFactory(WorkInTripFirebase(), requireActivity().application)
        viewModel = viewModelFactory.create(WorkInTripFragmentViewModel::class.java)
        viewModel.getTripData()


        return binding.root
    }


}