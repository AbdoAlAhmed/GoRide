package com.theideal.goride.model

data class Car(
    var id: String = "",
    var carType: String = "",
    var carModel: String = "",
    var carNumber: String = "",
    var carColor: String = "",
    var seats: String = "",
    var status: String,
    var workInLine: Boolean = false,
) {
    constructor() : this("", "", "","", "", "", "")
}

data class DriverHistory(
    val id: String = "",
    val ratingStars: String,
    val pickUpLocation: Location,
    val dropOffLocation: Location? = null,
    val distance: Double? = null,
    val fareAmount: Double? = null,
    val car: Car,
    val rideStatus: String,
)

data class RatingDriver(val driverStars: Int, val comment: String, val carStars: Int)
enum class AvailabilityStatus { Available, NotAvailable, RideSharing }
data class VehicleInfo(
    val carImg: String,
    val numberRiders: String,
    val vehicleLicences: String
)


/*
 is it going to be part of this data class
     var paymentInfo: String = "",
    var rating: RatingDriver = RatingDriver(0, "", 0),
    var availabilityStatus: AvailabilityStatus = AvailabilityStatus.Available,
    var profilePicture: String = "",
    var userType: String = "driver",
    var driverHistory: String = "",
 */