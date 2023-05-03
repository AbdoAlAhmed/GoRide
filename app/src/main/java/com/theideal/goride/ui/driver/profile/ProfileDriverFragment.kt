package com.theideal.goride.ui.driver.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
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
        savedInstanceState: Bundle?,
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
        val intent = Intent(requireActivity(), ProfileActivity::class.java)
        viewModel.navTo.observe(viewLifecycleOwner) {
            when (it) {
                ProfileDriverViewModel.SettingNavigation.DestinationPreferences -> {
                    intent.putExtra("Fragment", "DestinationPreferences")
                    startActivity(intent)
                    viewModel.navigateToComplete()
                }
                ProfileDriverViewModel.SettingNavigation.Logout -> {
                    logoutDialog()

                }
                ProfileDriverViewModel.SettingNavigation.PrivacyPolicy -> {
                    intent.putExtra("Fragment", "PrivacyPolicy")
                    startActivity(intent)
                    viewModel.navigateToComplete()
                }
                ProfileDriverViewModel.SettingNavigation.Help -> {
                    intent.putExtra("Fragment", "Help")
                    startActivity(intent)
                    viewModel.navigateToComplete()
                }


                else -> {

                }
            }
        }
        viewModel.snackBar.observe(viewLifecycleOwner) {
            if (it != "") {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                viewModel.snackBarComplete()
            }
        }
        binding.cardUserInfo.root.setOnClickListener {
            intent.putExtra("Fragment", "")
            startActivity(intent)
        }
        return binding.root

    }

    private fun logoutDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val dialogCreator = dialogBuilder.create()
        dialogCreator.setTitle("Logout")
        dialogCreator.setMessage("Are you sure you want to logout?")

        dialogCreator.setButton(AlertDialog.BUTTON_POSITIVE, "Logout") { _, _ ->
            viewModelAuth.logoutBoth()
            requireActivity().apply {
                startActivity(
                    Intent(
                        this,
                        com.theideal.goride.ui.auth.AuthenticationActivity::class.java
                    )
                )
                requireActivity().finish()
            }
            dialogCreator.dismiss()
        }
        dialogCreator.show()
    }

}