package com.sg.slightlyred.canberra.data.model

import android.location.Location

data class LocationResponse(var location: Location?, var errorMessage: String = "") {
    fun isError(): Boolean {
        return location == null
    }
}