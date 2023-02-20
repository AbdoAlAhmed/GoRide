package com.theideal.goride.model

data class Driver(
    var vehicleInfo: VehicleInfo = VehicleInfo("", CarType.Taxi),
    var DriverLicenseImg: String = "",
    var paymentInfo: String = "",
    var rating: RatingDriver = RatingDriver(0, "", 0),
    var availabilityStatus: AvailabilityStatus = AvailabilityStatus.Available,
    var profilePicture: String = "",
    var driverHistory: DriverHistory = DriverHistory("", "", Location(0.0, 0.0), null, null, null, Driver(), ""),
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
data class VehicleInfo(val carImg: String, val carType: CarType)
enum class CarType { Taxi, MicroBus, MiniBus, Bus, TinyVan }

