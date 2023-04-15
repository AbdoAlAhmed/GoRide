package com.theideal.goride.ui.driver.home.home_services.suggest_trip

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.theideal.goride.databinding.DialogSuggestTripBinding
import com.theideal.goride.databinding.FragmentSuggestTripDriverBinding
import com.theideal.goride.model.TripsLine
import timber.log.Timber

class SuggestTripsDriverFragment : Fragment() {

    private lateinit var viewModel: SuggestTripsViewModel
    private lateinit var binding: FragmentSuggestTripDriverBinding
    private val tripsLine = TripsLine()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSuggestTripDriverBinding.inflate(layoutInflater, null, false)
        val viewModelFactory = SuggestTripsViewModelFactory(FirebaseSuggestTrips())
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[SuggestTripsViewModel::class.java]
        viewModel.getSuggestTrips()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.rvSuggestTrips.adapter = SuggestTripsAdapter(SuggestTripsAdapter.OnClick {

        })
        viewModel.addDialog.observe(viewLifecycleOwner) {
            if (it) {
                suggestDialog()
            }
        }
        return binding.root
    }

    private fun suggestDialog() {
        val dialogBuild = AlertDialog.Builder(requireContext())
        val createDialog = dialogBuild.create()
        val view = DialogSuggestTripBinding.inflate(layoutInflater, null, false)
        createDialog.setView(view.root)
        view.tripsLine = tripsLine
        createDialog.show()
    }


}