package com.sg.slightlyred.canberra.constants

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class ServiceOperator(val fullName: String) {
    SBST("SBS Transit"),
    SMRT("SMRT Corporation"),
    GAS("Go-Ahead Singapore"),
    TTS("Tower Transit Singapore"),
    UNKNOWN("Unknown")
}