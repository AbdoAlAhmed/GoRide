package com.theideal.goride.ui.rider.home.services.availableTrip

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.material.textfield.TextInputEditText
import com.theideal.goride.R
import com.theideal.goride.databinding.FragmentAvailableTripBinding
import com.theideal.goride.model.GeoFencing
import timber.log.Timber
import kotlin.properties.Delegates


class AvailableTripFragment : Fragment() {
    private lateinit var binding: FragmentAvailableTripBinding
    private lateinit var viewModel: AvailableTripsViewModel
    private var tripId by Delegates.notNull<Int>()
    private lateinit var geoFencing: GeoFencing
    private lateinit var geofencingClient: GeofencingClient
    private val requestCodeGeofence = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAvailableTripBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[AvailableTripsViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        geofencingClient = LocationServices.getGeofencingClient(requireActivity())
        viewModel.setData()
        binding.rvAvailableTrips.adapter = AvailableTripAdapter(AvailableTripAdapter.OnClick {
            tripId = it.id
            geoFencing = it.PickUpLocation
            createGeoFencing()
        })
        binding.btnTestTrigger.setOnClickListener {
            geoFenceTrigger()
        }
        return binding.root
    }

    private fun createGeoFencing(): Geofence {
        return Geofence.Builder()
            .setRequestId(geoFencing.id)
            .setCircularRegion(
                geoFencing.latitude,
                geoFencing.longitude,
                10.0f
            )
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(
                Geofence.GEOFENCE_TRANSITION_ENTER
                        or Geofence.GEOFENCE_TRANSITION_EXIT
            )
            .build()

    }

    @SuppressLint("MissingPermission")
    fun geoFenceTrigger() {
        val geofenceRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(createGeoFencing())
            .build()
        Timber.i(geofenceRequest.toString())


       val a = geofencingClient.addGeofences(geofenceRequest, getGeoFenceAvailableTrip())
        a.addOnFailureListener {
            Timber.i("failur to add $it")
        }.addOnSuccessListener {
            Timber.i("success $it")
        }
    }

    private fun getGeoFenceAvailableTrip(): PendingIntent {
        val intent = Intent(requireContext(), BroadCastReceiverTrip::class.java)
        return PendingIntent.getBroadcast(
            requireContext(),
            requestCodeGeofence,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun showDialogTest() {
        val builder = AlertDialog.Builder(requireContext())
        val dialog = builder.create()
        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_pick_a_trip, null, false)
        dialog.setView(view)
        dialog.show()
    }


}