package com.theideal.goride.model


data class Rider(
    val id: String,
    val name: String,
    val email: String,
    val userType: String,
    val typeOfPerson: TypeOfPerson,
    val age: Int,
    private val password: String,
    val phone: String,
    val paymentInfo: String,
    val riderHistory: RiderHistory,
    val profilePicture: String,
    val rating: RatingRider
) {
    fun getPassword() = this.password

}

data class RiderHistory(
    val rating: RatingRider,
    val pickUpLocation: Location,
    val dropOffLocation: Location? = null,
    val distance: Double? = null,
    val fareAmount: Double? = null,
    val driver: Driver,
    val rideStatus: RideStatus
)

enum class TypeOfPerson { Female, Male }
data class RatingRider(val stars: Int, val comment: String)
data class Location(val latitude: Double, val longitude: Double)
enum class RideStatus { InProgress, Completed, Cancelled }
