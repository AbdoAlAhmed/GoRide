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