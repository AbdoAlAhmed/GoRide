package com.theideal.goride.ui.driver.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.DialogLogoutBinding
import com.theideal.goride.databinding.FragmentProfileDriverBinding
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.User
import com.theideal.goride.ui.auth.AuthenticationViewModel
import com.theideal.goride.ui.auth.AuthenticationViewModelFactory
import com.theideal.goride.ui.driver.profile.items.ProfileActivity

class ProfileDriverFragment : Fragment() {
    private lateinit var binding: FragmentProfileDriverBinding
    private lateinit var viewModel: ProfileDriverViewModel
    private lateinit var viewModelAuth: AuthenticationViewModel
    private lateinit var viewModelAuthFactory: AuthenticationViewModelFactory
    private val User = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentProfileDriverBinding.inflate(inflater, container, false)
        val viewModelFactory = ProfileDriverViewModelFactory(ProfileDriverFirebase())
        viewModel =
            ViewModelProvider(this, viewModelFactory)[(ProfileDriverViewModel::class.java)]
        viewModelAuthFactory = AuthenticationViewModelFactory(FirebaseAuthModel())
        viewModelAuth =
            ViewModelProvider(this, viewModelAuthFactory)[AuthenticationViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getAndUpdateUserInformation()
        binding.rvSettings.adapter = ProfileDriverAdapter(ProfileDriverAdapter.OnClick {
            viewModel.navigateTo(it)
        })
        viewModel.navTo.observe(viewLifecycleOwner) {
            when (it) {
                ProfileDriverViewModel.SettingNavigation.DestinationPreferences -> {
                    startActivity(Intent(requireActivity(), ProfileActivity::class.java))
                }
                ProfileDriverViewModel.SettingNavigation.Logout -> {
                    logoutDialog()
                }
                else -> {

                }
            }
        }
        return binding.root

    }

    private fun logoutDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val dialogCreator = dialogBuilder.create()
        val dialogView = DialogLogoutBinding.inflate(layoutInflater, null, false)
        dialogCreator.setView(dialogView.root)
        dialogCreator.show()
        dialogView.btnLogout.setOnClickListener {

            viewModelAuth.logoutBoth()
            requireActivity().apply {
                startActivity(Intent(this, com.theideal.goride.ui.auth.AuthenticationActivity::class.java))
                requireActivity().finish()
            }
            dialogCreator.dismiss()
        }
    }

}