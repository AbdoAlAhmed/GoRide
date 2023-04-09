package com.theideal.goride.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.theideal.goride.databinding.FragmentCarInfoBinding
import com.theideal.goride.model.Car
import com.theideal.goride.model.FirebaseAuthModel


class CarInfo : Fragment() {
    private lateinit var binding: FragmentCarInfoBinding
    private lateinit var viewModel: AuthenticationViewModel
    private val car = Car()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCarInfoBinding.inflate(inflater, container, false)
        val viewModelFactory = AuthenticationViewModelFactory(FirebaseAuthModel())
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[AuthenticationViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.car = car
        viewModel.isSignUpDriverCompleted.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(CarInfoDirections.actionCarInfoToSignInFragment2())
                viewModel.signUpDriverCompletedDoneNav()

            }
        }

        return binding.root
    }

}