package com.theideal.goride.model

data class DriverStatus(
    var id: String,
    var status: String,
    var userRef: String,
    var realTimeLocation: Location
) {
    constructor() : this("", "", "", Location(0.0, 0.0))
}
