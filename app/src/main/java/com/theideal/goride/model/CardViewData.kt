package com.theideal.goride.model

import android.net.Uri

data class CardViewData(
    var id: Int = 0,
    var title: String = "",
    var subTitle: String = "",
    var image: String = "",
) {
    constructor() : this(0, "", "", "")
}


