package com.theideal.goride.model

data class TripsLine(
    val tripsLineId: String,
    val startDestination: Location,
    val endDestination: Location,
    var fare: String,
    val duration: String,
    val distance: String,
    val tripsLineStatus: String,
    val HMRequest: Int,
    val IsHSupported: Boolean

) {
    constructor() : this("", Location(), Location(), "", "", "", "", 0, false)
}
// trip line status = "suggest" = pass the review waiting for support  or "review" first step  or "available" last step