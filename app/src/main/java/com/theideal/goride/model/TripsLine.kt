package com.theideal.goride.model

data class TripsLine(
    val tripsLineId: Int,
    val startDestination: Location,
    val endDestination: Location,
    var fare: Double,
    val duration: String,
    val distance: String,
    val tripsLineStatus: String,
    val HMRequest: Int,
    val IsHSupported: Boolean

) {
    constructor() : this(0, Location(), Location(), 0.00, "", "", "", 0, false)
}
// trip line status = "suggest" = pass the review waiting for support  or "review" first step  or "available" last step