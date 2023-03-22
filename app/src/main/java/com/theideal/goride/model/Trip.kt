package com.theideal.goride.model



data class Trip(
    var tripId: String = "",
    var startDestination: Location,
    var endDestination: Location,
    var numberOfRiders: String = "",
    var duration: String = "",
    var fare: Double = 0.0,
    var date: String = "",
    var whenToRide: String,
    var distances: String = "",
    var usersId: List<String>,
    var driverId: String
) {
    constructor() : this(
        "", Location(), Location(), "", "", 0.0, "", "", "", arrayListOf(), ""
    )
}
