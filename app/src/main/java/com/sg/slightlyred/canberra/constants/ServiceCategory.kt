package com.sg.slightlyred.canberra.constants

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class ServiceCategory(val categoryName: String) {
    EXPRESS("Express"),
    FEEDER("Feeder"),
    INDUSTRIAL("Industrial"),
    TOWNLINK("Townlink"),
    TRUNK("Trunk"),
    TWO_TIER_FLAT_FEE("2 Tier Flat Fee"),
    FLAT_FEE_110("Flat Fee, $1.10"),
    FLAT_FEE_190("Flat Fee, $1.90"),
    FLAT_FEE_350("Flat Fee, $3.50"),
    FLAT_FEE_380("Flat Fee, $3.80"),
    UNKNOWN("Unknown")
}