package com.theideal.goride.model

data class TripsLine(
    val tripsLineId: Int,
    val startDestination: Location,
    var endDestination: Location,
    val fare: Double,
    val duration: String,
    val distance: String,
    val tripsLineStatus: String,
    val HMRequest: String,
    val IsHSupported: Boolean
) {
    constructor() : this(0, Location(), Location(), 0.0, "", "", "", "", false)
}
// trip line status = "suggest" = pass the review waiting for support  or "review" first step  or "available" last step