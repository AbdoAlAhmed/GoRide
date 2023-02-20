package com.theideal.goride.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.theideal.goride.databinding.FragmentSigninBinding
import com.theideal.goride.model.FirebaseModel
import com.theideal.goride.model.User
import com.theideal.goride.viewmodel.AuthenticationViewModel
import com.theideal.goride.viewmodel.AuthenticationViewModelFactory


class SignInFragment : Fragment() {
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var binding: FragmentSigninBinding
    private val user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        val viewModelFactory = AuthenticationViewModelFactory(FirebaseModel())
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[AuthenticationViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.user = user


        viewModel.navToSignUp.observe(viewLifecycleOwner){
            if (it){
                findNavController().navigate(SignInFragmentDirections.actionSignInFragment2ToSignUpFragmentDriver())
                viewModel.doneNavToSignUp()
            }
        }

        return binding.root
    }


}