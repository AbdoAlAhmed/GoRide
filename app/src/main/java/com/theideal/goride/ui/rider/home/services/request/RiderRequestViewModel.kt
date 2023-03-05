package com.theideal.goride.ui.rider.home.services.request

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import timber.log.Timber

class RiderRequestViewModel(private val application: Application) : AndroidViewModel(application) {


    private val _query = MutableLiveData<String>()
    val query: LiveData<String>
        get() = _query
    private val _permissionGranted = MutableLiveData<Boolean>()
    val permissionGranted: LiveData<Boolean>
        get() = _permissionGranted


    fun getQuery(string: String) {
        _query.value = string
    }

    fun getAutoCompletePrediction(query: String): LiveData<List<AutocompletePrediction>> {
        val autoCompleteClient = Places.createClient(application)
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .build()
        val liveData = MutableLiveData<List<AutocompletePrediction>>()
        autoCompleteClient.findAutocompletePredictions(request)
            .addOnSuccessListener {
                liveData.value = it.autocompletePredictions
            }
            .addOnFailureListener {
                Timber.i(it, "Failed Auto Complete")
            }
        return liveData
    }


    fun checkPermission(context: Context, permission: String) {
        _permissionGranted.value = ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED

    }

    fun requestPermission(activity: Activity, permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(
            activity, permissions, requestCode
        )
    }

    fun setPermissionGranted() {
        _permissionGranted.value = true
    }

    fun setPermissionDenied() {
        _permissionGranted.value = false
    }

}