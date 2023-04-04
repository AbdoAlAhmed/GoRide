package com.theideal.goride.model

import java.util.*


data class Trip(
    var tripId: String = "",
    var startDestination: Location,
    var endDestination: Location,
    var numberOfRiders: Int,
    var numberOfSeats: Int,
    var duration: String = "",
    var fare: Double = 0.0,
    var date: String = Calendar.getInstance().get(Calendar.DATE).toString(),
    var whenToRide: String,
    var distances: String = "",
    var riderId: ArrayList<String>,
    var driverId: String,
    var tripStatus: String = ""
) {
    constructor() : this(

        "", Location(), Location(), 0, 0, "", 0.0, "", ""
        , "", arrayListOf(), "", ""
    )
}
