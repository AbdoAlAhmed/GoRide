package com.theideal.goride.model

data class CardViewData(
    var id: Int = 0,
    var title: String = "",
    var subtitle: String = "",
    var image: String = "",
    var label: String
) {
    constructor() : this(0, "", "", "", "")
}


