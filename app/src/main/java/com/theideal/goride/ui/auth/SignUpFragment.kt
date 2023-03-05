package com.theideal.goride.ui.auth

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.theideal.goride.R
import com.theideal.goride.databinding.FragmentSignUpBinding
import com.theideal.goride.model.Driver
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.Rider
import com.theideal.goride.model.User

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: AuthenticationViewModel
    private val user = User()
    private val _rider = Rider()
    private val _driver = Driver()
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
        viewModel.showDialog.observe(viewLifecycleOwner) {
            if (it) {
                showDialog()
            }
        }
        viewModel.isSignUpRider.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
            }
        }
        viewModel.navToSignUpPage2Driver.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentDriverToSignUpPage2Driver())
                viewModel.doneNavToSignUpPage2Driver()
            }
        }

        return binding.root
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(activity)
        val layout = LayoutInflater.from(activity).inflate(R.layout.dialog_choose, null)
        val dialog = builder.create()
        val driver = layout.findViewById<Button>(R.id.sign_up_driver)
        dialog.setTitle(R.string.choose)
        dialog.setView(layout)
        dialog.show()
        driver.setOnClickListener {
            viewModel.navToSignUpPage2Driver()
            dialog.dismiss()
        }
        val rider = layout.findViewById<Button>(R.id.sign_up_rider)
        rider.setOnClickListener {
            viewModel.signUpRiderComplete(user)
            dialog.dismiss()
        }
    }


}