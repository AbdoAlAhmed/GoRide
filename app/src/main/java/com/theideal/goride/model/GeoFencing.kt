package com.theideal.goride.model

data class GeoFencing(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val radius: String
) {
    constructor() : this("", 0.0, 0.0, "")
}