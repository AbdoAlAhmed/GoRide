package com.theideal.goride.ui.driver.profile.items.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.theideal.goride.databinding.FragmentAccountInformationBinding
import com.theideal.goride.model.User


class AccountInformationFragment : Fragment() {
    private lateinit var viewModel: AccountInformationViewModel
    private lateinit var viewModelFactory: AccountInformationViewModelFactory
    private lateinit var binding: FragmentAccountInformationBinding
    private val user = User()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountInformationBinding.inflate(inflater, container, false)
        viewModelFactory = AccountInformationViewModelFactory(AccountInformationFirebase())
        viewModel = viewModelFactory.create(AccountInformationViewModel::class.java)
        binding.viewModel = viewModel
        binding.user = user
        binding.lifecycleOwner = this
        return binding.root
    }

}