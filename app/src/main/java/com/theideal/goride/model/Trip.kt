package com.theideal.goride.model


data class Trip(
    var id: Int = 0,
    var startDestination: String = "",
    var endDestination: String = "",
    var PickUpLocation: GeoFencing = GeoFencing("",0.0, 0.0, ""),
    var duration: String = "",
    var fare: Double = 0.0,
    var date: String = "",
    var distances: String = "",
    var rider: Rider,
    var driver: Driver
) {
    constructor() : this(0, "", ""
        , GeoFencing("",0.0, 0.0,""), "", 0.0, "", "", Rider(), Driver())
}
