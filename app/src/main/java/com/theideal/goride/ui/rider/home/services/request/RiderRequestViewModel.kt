package com.theideal.goride.ui.rider.home.services.request

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import timber.log.Timber

class RiderRequestViewModel(private val application: Application)
    : AndroidViewModel(application) {


    private val _query = MutableLiveData<String>()
    val query: LiveData<String>
        get() = _query



    fun getQuery (string: String){
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

}