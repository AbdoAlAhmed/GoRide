package com.theideal.goride.model


data class Trip(
    var tripId: Int = 0,
    var startDestination: Location,
    var endDestination: Location,
    var numberOfRiders: String = "",
    var duration: String = "",
    var fare: Double = 0.0,
    var date: String = "",
    var whenToRide: String,
    var distances: String = "",

    ) {
    constructor() : this(
        0, Location(), Location(), "", "", 0.0, "", "", ""
    )
}
