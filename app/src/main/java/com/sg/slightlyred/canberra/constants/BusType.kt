package com.sg.slightlyred.canberra.constants

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class BusType(val description: String) {
    SD("Single Deck"),
    DD("Double Deck"),
    BD("Bendy"),
    UNKNOWN("Unknown")
}