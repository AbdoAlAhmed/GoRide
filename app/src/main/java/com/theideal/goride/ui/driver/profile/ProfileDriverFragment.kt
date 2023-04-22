package com.theideal.goride.ui.driver.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.FragmentProfileDriverBinding
import com.theideal.goride.model.User
import com.theideal.goride.ui.driver.profile.items.ProfileActivity

class ProfileDriverFragment : Fragment() {
    private lateinit var binding: FragmentProfileDriverBinding
    private lateinit var viewModel: ProfileDriverViewModel
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
                else -> {

                }
            }
        }
        return binding.root

    }

}