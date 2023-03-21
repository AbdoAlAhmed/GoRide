package com.theideal.goride.model

data class Location(
    var latitude: Double,
    var longitude: Double,
    var destinationName: String
) {
    constructor() : this(0.0, 0.0, "")
}
