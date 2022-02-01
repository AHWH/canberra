package com.sg.slightlyred.canberra.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LtaDataMallBusArrivalResponse<BusArrival> (
    @Json(name = "odata.metadata")
    var metadata: String,
    @Json(name = "BusStopCode")
    var busStopCode: String,
    @Json(name = "Services")
    var services: List<BusArrival>
)