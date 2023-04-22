package com.theideal.goride.ui.driver.profile

data class Setting (val nameOfTheSetting: String , val label: String,val id: Int = 0){
    constructor() : this("", "",0)
}