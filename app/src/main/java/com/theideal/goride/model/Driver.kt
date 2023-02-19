package com.theideal.goride.model

data class Driver(
    val id: String,
    val name: String,
    val email: String,
    val userType: String ,
    val age: Int,
    private val password: String,
    val phone: String,
    val vehicleInfo: VehicleInfo,
    val DriverLicenseImg: String,
    val paymentInfo: String,
    val rating: RatingDriver,
    val rideHistory: DriverHistory,
    val availabilityStatus: AvailabilityStatus,
    val profilePicture: String
){
    fun getPassword() = password
}

data class DriverHistory(
    val rating: RatingDriver,
    val pickUpLocation: Location,
    val dropOffLocation: Location? = null,
    val distance: Double? = null,
    val fareAmount: Double? = null,
    val driver: Driver ,
    val rideStatus:RideStatus,
)

data class RatingDriver(val driverStars: Int, val comment: String,val carStars: Int)
enum class AvailabilityStatus{Available , NotAvailable , RideSharing}
data class VehicleInfo(val carImg: String , val carType: CarType)
enum class CarType{Taxi , MicroBus , MiniBus , Bus , TinyVan}

