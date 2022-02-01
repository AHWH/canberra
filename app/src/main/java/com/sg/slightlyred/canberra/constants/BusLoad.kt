package com.sg.slightlyred.canberra.constants

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class BusLoad(val description: String) {
    SEA("Seats Available"),
    SDA("Standing Available"),
    LSD("Limited Standing"),
    UNKNOWN("Unknown")
}