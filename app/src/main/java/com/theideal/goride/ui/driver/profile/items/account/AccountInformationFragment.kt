package com.theideal.goride.ui.driver.profile.items.account

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.theideal.goride.databinding.DialogUpdateInforamtionBinding
import com.theideal.goride.databinding.FragmentAccountInformationBinding
import com.theideal.goride.model.FirebaseAuthModel
import com.theideal.goride.model.User
import com.theideal.goride.ui.auth.AuthenticationActivity
import com.theideal.goride.ui.auth.AuthenticationViewModel
import com.theideal.goride.ui.auth.AuthenticationViewModelFactory
import kotlinx.coroutines.launch
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
        binding.viewModelAuth = viewModelAuth
        binding.user = user
        binding.lifecycleOwner = this
        viewModel.updateDialog.observe(viewLifecycleOwner) {
            if (it) {
                updateDialog()
            }
        }
        viewModelAuth.deleteUserAccountDialog.observe(viewLifecycleOwner) {
            if (it) {
                deleteAccountDialog()
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

    private fun deleteAccountDialog() {
        val dialogAlert = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogAlert.create()
        dialogCreate.setTitle("Delete Account")
        dialogCreate.setMessage(
            "Are you sure you want to delete your account?" +
                    " This action cannot be undone"
        )
        dialogCreate.setButton(AlertDialog.BUTTON_POSITIVE, "Yes") { _, _ ->
            lifecycleScope.launch {
                try {
                    viewModelAuth.deleteAccount()
                    viewModelAuth.logoutBoth()
                    dialogCreate.dismiss()
                    requireActivity().finish()
                    startActivity(Intent(requireContext(), AuthenticationActivity::class.java))
                }catch (e:Exception){
                    Timber.e(e)
                }

            }
        }
        dialogCreate.setButton(AlertDialog.BUTTON_NEGATIVE, "No") { _, _ ->
            dialogCreate.dismiss()
            requireActivity().finish()

        }
        dialogCreate.show()
    }

}