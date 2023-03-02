package com.theideal.goride.model

import java.util.Date

data class Trip(
    var id: Int = 0 ,
    var startDestination: String = "",
    var endDestination: String = "",
    var duration: String = "",
    var fare : Double = 0.0 ,
    var date: Date ,
    var distances : String ,
    var rider: Rider ,
    var driver: Driver
)
