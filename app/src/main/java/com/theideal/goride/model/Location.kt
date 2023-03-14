package com.theideal.goride.model

data class Location(
    var latitude: Double,
    var longitude: Double
) {
    constructor() : this(0.0, 0.0)
}
