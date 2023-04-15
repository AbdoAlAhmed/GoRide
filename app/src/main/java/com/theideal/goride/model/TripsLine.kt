package com.theideal.goride.model

data class TripsLine(
    val tripsLineId: Int,
    var startDestination: Location,
    var endDestination: Location,
    val fare: Double,
    val duration: String,
    val distance: String,
    val tripsLineStatus: String,
    val HMRequest: Int,
    val IsHSupported: Boolean
)
// trip line status = "suggest" = pass the review waiting for support  or "review" first step  or "available" last step