package com.theideal.goride.model

data class DriverStatus(
    var statusId: String,
    var status: String,
    var userRef: String,
    var realTimeLocation: Location,
    var user: User
) {
    constructor() : this("", "", "", Location(),User())
}
