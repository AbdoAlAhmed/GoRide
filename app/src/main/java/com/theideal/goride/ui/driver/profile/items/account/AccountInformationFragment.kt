package com.theideal.goride.ui.driver.profile.items.account

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.theideal.goride.databinding.DialogUpdateInforamtionBinding
import com.theideal.goride.databinding.FragmentAccountInformationBinding
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.User
import com.theideal.goride.ui.auth.AuthenticationViewModel
import com.theideal.goride.ui.auth.AuthenticationViewModelFactory
import timber.log.Timber


class AccountInformationFragment : Fragment() {
    private lateinit var viewModel: AccountInformationViewModel
    private lateinit var viewModelFactory: AccountInformationViewModelFactory
    private lateinit var viewModelAuth: AuthenticationViewModel
    private lateinit var viewModelAuthFactory: AuthenticationViewModelFactory
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
        viewModelAuthFactory = AuthenticationViewModelFactory(FirebaseAuthModel())
        viewModelAuth =
            viewModelAuthFactory.create(AuthenticationViewModel::class.java)
        binding.viewModel = viewModel
        binding.user = user
        binding.lifecycleOwner = this
        viewModel.updateDialog.observe(viewLifecycleOwner) {
            if (it) {
                Timber.i("updateDialog")
                updateDialog()
            }
        }
        return binding.root
    }

    private fun updateDialog() {
        val dialogAlert = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogAlert.create()
        val dialogView = DialogUpdateInforamtionBinding.inflate(layoutInflater)
        dialogCreate.setTitle("Update Information")
        dialogCreate.setView(dialogView.root)
        dialogCreate.show()
        dialogView.btnCancelUpdateInformation.setOnClickListener {
            viewModel.updateDialogDone()
            dialogCreate.dismiss()
            requireActivity().finish()
        }
        dialogView.btnConfirmUpdateInformation.setOnClickListener {
            viewModel.updateUserInfo()
            viewModel.updateDialogDone()
            dialogCreate.dismiss()
            requireActivity().finish()
        }

    }

}