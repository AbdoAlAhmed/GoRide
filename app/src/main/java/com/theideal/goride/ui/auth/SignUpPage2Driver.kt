package com.theideal.goride.ui.auth

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.FragmentSignUpPage2DriverBinding
import com.theideal.goride.model.Driver
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.User

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SignUpPage2Driver : Fragment() {
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var binding: FragmentSignUpPage2DriverBinding
    private var RequestCode = 101
    private val driver = Driver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpPage2DriverBinding.inflate(inflater, container, false)
        val viewModelFactory = AuthenticationViewModelFactory(FirebaseAuthModel())
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[AuthenticationViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.driver = driver
        val user = SignUpPage2DriverArgs.fromBundle(requireArguments()).user
        binding.user = user
        // permission
        viewModel.checkPermission(
            requireContext().applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
        viewModel.requestPermission(requireActivity(), permissions, RequestCode)


        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != null && requestCode == RequestCode) {
            viewModel.setPermissionGranted()
        } else {
            viewModel.setPermissionDenied()
        }
    }
}