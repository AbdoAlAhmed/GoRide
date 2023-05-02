package com.theideal.goride.ui.driver.profile.items.destination

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theideal.goride.model.TripsLine
import timber.log.Timber

class DestinationPreferenceViewModel(
    private val db: DestinationPreferenceFirebase,
    private val app: Application
) : ViewModel() {

    private val _tripsLineListToWorkOnIt = MutableLiveData<ArrayList<TripsLine>>()
    val tripsLineListToWorkOnIt: LiveData<ArrayList<TripsLine>>
        get() = _tripsLineListToWorkOnIt

    private val _tripLineListThatYouWorkOn = MutableLiveData<ArrayList<TripsLine>>()
    val tripLineListThatYouWorkOn: LiveData<ArrayList<TripsLine>>
        get() = _tripLineListThatYouWorkOn

    private val _dialog = MutableLiveData<Boolean>()
    val dialog: LiveData<Boolean>
        get() = _dialog


    private val tripList: MutableLiveData<ArrayList<String>> = MutableLiveData()

    init {
        db.getTripData {
            _tripsLineListToWorkOnIt.value = it
        }



    }

    fun showDialog() {
        _dialog.value = true
    }

    fun hideDialog() {
        _dialog.value = false
    }


    fun addTrip() {
        Timber.d("_tripList: ${tripList.value}")
        try {
            db.addAndGetTrip(tripList.value!!) {

            }
        } catch (e: Exception) {
            Timber.d("Exception: $e")
        }

    }

    fun addTripToList(trip: TripsLine) {
        Timber.d("_tripList: ${tripList.value}")
        if (tripList.value != null) {
            Timber.d("_tripList is not null")
            if (tripList.value!!.contains(trip.tripsLineId)) {
                Timber.d("_tripList contains trip")
                tripList.value!!.remove(trip.tripsLineId)
                Timber.d("_tripList: ${tripList.value}")
            } else {
                Timber.d("!_tripList contains trip")
                tripList.value!!.add(trip.tripsLineId)
                Timber.d("_tripList: ${tripList.value}")
            }
        } else {
            Timber.d("_tripList is null")
            tripList.value = arrayListOf(trip.tripsLineId)
        }


    }
}