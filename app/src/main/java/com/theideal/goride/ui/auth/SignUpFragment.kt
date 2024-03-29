package com.theideal.goride.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.theideal.goride.databinding.FragmentSignUpBinding
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.User

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: AuthenticationViewModel
    private val user = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val viewModelFactory = AuthenticationViewModelFactory(FirebaseAuthModel())
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthenticationViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.user = user

        viewModel.isSignUpRider.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
            }
        }
        viewModel.navToSignUpPage2Driver.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    SignUpFragmentDirections.actionSignUpFragmentDriverToSignUpPage2Driver()
                )
                viewModel.doneNavToSignUpPage2Driver()
            }
        }
        viewModel.snackBar.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val snackBar = Snackbar.make(
                    binding.root,
                    it,
                    Snackbar.LENGTH_LONG
                )
                snackBar.show()
                viewModel.doneSnackBar()
            }
        }

        return binding.root
    }


}