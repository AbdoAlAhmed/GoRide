package com.theideal.goride.ui.driver.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.FragmentSerivcesDriverBinding
import com.theideal.goride.ui.rider.CardViewAdapter

class ServicesDriverFragment : Fragment() {
    private lateinit var viewModel: ServicesDriverViewModel
    private lateinit var binding: FragmentSerivcesDriverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSerivcesDriverBinding.inflate(layoutInflater, null, false)
        val viewModelFactory = ServicesDriverViewModelFactory(ServicesDriverFirebase())
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[ServicesDriverViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getServicesDriver_()

        binding.driverServicesInFragmentServices.adapter = CardViewAdapter(CardViewAdapter.OnClick {

        })


        return binding.root
    }


}