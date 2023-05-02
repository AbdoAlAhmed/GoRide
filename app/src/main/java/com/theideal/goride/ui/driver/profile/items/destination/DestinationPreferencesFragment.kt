package com.theideal.goride.ui.driver.profile.items.destination

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.DialogChooseTripBinding
import com.theideal.goride.databinding.FragmentDestinationPreferencesBinding


class DestinationPreferencesFragment : Fragment() {
    private lateinit var viewModel: DestinationPreferenceViewModel
    private lateinit var binding: FragmentDestinationPreferencesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDestinationPreferencesBinding.inflate(inflater, container, false)
        val viewModelFactory = DestinationPreferenceViewModelFactory(
            DestinationPreferenceFirebase(),
            requireActivity().application
        )
        viewModel =
            ViewModelProvider(this, viewModelFactory)[(DestinationPreferenceViewModel::class.java)]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.rvTripsLine.adapter =
            DestinationPreferenceAdapter(DestinationPreferenceAdapter.OnClickListener {
                viewModel.addTripToList(it)
            })

        viewModel.dialog.observe(viewLifecycleOwner){
            if (it) {
                chooseTripDialog()
            }
        }

        return binding.root
    }

    fun chooseTripDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val dialogCreator = dialogBuilder.create()
        val dialogView = DialogChooseTripBinding.inflate(layoutInflater, null, false)
        dialogCreator.setView(dialogView.root)
        dialogView.btnOk.setOnClickListener {
            viewModel.addTrip()
            requireActivity().finish()
            dialogCreator.dismiss()
        }
        dialogCreator.show()

    }

}