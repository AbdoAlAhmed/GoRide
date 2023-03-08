package com.theideal.goride.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.theideal.goride.R
import com.theideal.goride.databinding.FragmentForgetPasswordBinding
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.User


class ForgetPasswordFragment : Fragment() {
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var binding: FragmentForgetPasswordBinding
    private val user = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        val viewModelFactory = AuthenticationViewModelFactory(FirebaseAuthModel())
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[AuthenticationViewModel::class.java]
        binding.viewModel = viewModel
        binding.user = user
        viewModel.snackBar.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.doneSnackBar()
                if (it == "Email sent successfully") {
                    findNavController().popBackStack()
                }
            }
        }

        return binding.root
    }

}