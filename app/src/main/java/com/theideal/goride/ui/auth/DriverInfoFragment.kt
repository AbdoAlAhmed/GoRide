package com.theideal.goride.ui.auth

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.theideal.goride.databinding.FragmentDriverInfoBinding
import com.theideal.goride.model.Car
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.ImageName


class DriverInfoFragment : Fragment() {
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var binding: FragmentDriverInfoBinding
    private var RequestCodePermission = 101
    private val RequestCodeImage = 102
    private val car = Car()
    private val imageNameClass = ImageName()
    private lateinit var imageName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDriverInfoBinding.inflate(inflater, container, false)
        val viewModelFactory = AuthenticationViewModelFactory(FirebaseAuthModel())
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[AuthenticationViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.car = car
        binding.imageName = imageNameClass
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
        viewModel.requestPermission(requireActivity(), permissions, RequestCodePermission)

        viewModel.snackBar.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.doneSnackBar()
            }
        }
        viewModel.imageUpload.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                imageName = it
                imagePicker()
            }
        }
        viewModel.navToCarInfoFragment.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    DriverInfoFragmentDirections.actionDriverInfoToCarInfo()
                )
                viewModel.navToCarInfoFragmentComplete()
            }
        }

        return binding.root
    }

    private fun imagePicker() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(480, 480)
            .start(RequestCodeImage)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != null && requestCode == RequestCodePermission) {
            viewModel.setPermissionGranted()
        } else {
            viewModel.setPermissionDenied()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RequestCodeImage) {
            val fileUri = data?.data

            viewModel.uploadImage(fileUri!!, imageName)
            viewModel.setSnackBar("Image Uploaded + $imageName")
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            viewModel.setSnackBar(ImagePicker.getError(data)!!)
        } else {
            viewModel.setSnackBar("Task Cancelled")
        }
    }
}