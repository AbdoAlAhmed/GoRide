package com.theideal.goride.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.theideal.goride.databinding.FragmentSigninBinding
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.User
import com.theideal.goride.ui.auth.sigin.SignInViewModel
import com.theideal.goride.ui.driver.DriverActivity
import com.theideal.goride.ui.rider.RiderActivity


class SignInFragment : Fragment() {
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var viewModel2: SignInViewModel
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
        val viewModelFactory = AuthenticationViewModelFactory(FirebaseAuthModel())
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[AuthenticationViewModel::class.java]
        viewModel2 = SignInViewModel(FirebaseAuthModel())
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.viewModel2 = viewModel2
        binding.user = user


        viewModel.isSignIn()

        viewModel.navToSignUp.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragment2ToSignUpFragmentDriver())
                viewModel.doneNavToSignUp()
            }
        }
        viewModel.isSignInRider.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(
                    Intent(
                        requireActivity(), RiderActivity::class.java
                    )
                )
            }
        }
        viewModel.isSignInDriver.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(
                    Intent(
                        requireActivity(),
                        DriverActivity::class.java
                    )
                )

            }
        }
        viewModel.navToForgetPassword.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragment2ToForgetPasswordFragment())
                viewModel.doneNavToForgetPassword()
            }
        }

        viewModel.snackBar.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.doneSnackBar()
            }
        }

        return binding.root
    }
}