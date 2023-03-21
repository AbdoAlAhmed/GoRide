package com.theideal.goride.model


data class Rider(
    val paymentInfo: String = "",
    val riderHistory: RiderHistory = RiderHistory("", Location(0.0, 0.0,""), null, null, null, Driver(), ""),
    val profilePicture: String = "",
    val rating: RatingRider = RatingRider(0, ""),
    var userType: String = "rider",
    val user: User = User()
){

}
data class RiderHistory(
    val ratingStars: String,
    val pickUpLocation: Location,
    val dropOffLocation: Location? = null,
    val distance: Double? = null,
    val fareAmount: Double? = null,
    val driver: Driver,
    val rideStatus: String
)

enum class Gender { Female, Male }
data class RatingRider(val stars: Int, val comment: String)
enum class RideStatus { InProgress, Completed, Cancelled }
