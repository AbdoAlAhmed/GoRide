package com.theideal.goride.model

data class Driver(
    var vehicleInfo: VehicleInfo = VehicleInfo("", CarType.Taxi, "",""),
    var DriverLicenseImg: String = "",
    var paymentInfo: String = "",
    var rating: RatingDriver = RatingDriver(0, "", 0),
    var availabilityStatus: AvailabilityStatus = AvailabilityStatus.Available,
    var profilePicture: String = "",
    var userType: String = "driver",
    var driverHistory: String = "",
    var user: User = User()
) {
}

data class DriverHistory(
    val id: String = "",
    val ratingStars: String,
    val pickUpLocation: Location,
    val dropOffLocation: Location? = null,
    val distance: Double? = null,
    val fareAmount: Double? = null,
    val driver: Driver,
    val rideStatus: String,
)

data class RatingDriver(val driverStars: Int, val comment: String, val carStars: Int)
enum class AvailabilityStatus { Available, NotAvailable, RideSharing }
data class VehicleInfo(
    val carImg: String,
    val carType: CarType,
    val numberRiders: String,
    val vehicleLicences: String
)

enum class CarType { Taxi, MicroBus, MiniBus, Bus, TinyVan }

