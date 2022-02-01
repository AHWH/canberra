package com.sg.slightlyred.canberra.data.model.bus

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.objectbox.annotation.*
import io.objectbox.relation.ToMany

@Entity
@JsonClass(generateAdapter = true)
data class BusStop(
    @Id
    @Json(ignore = true)
    var id: Long = 0,
    @Index
    @Unique(onConflict = ConflictStrategy.REPLACE)
    @Json(name = "BusStopCode")
    var busStopCode: String,
    @Json(name = "RoadName")
    var roadName: String,
    @Json(name = "Description")
    var description: String,
    @Json(name = "Latitude")
    var latitude: String,
    @Json(name = "Longitude")
    var longitude: String
) {
    @Backlink
    @Json(ignore = true)
    lateinit var busRoutes: ToMany<BusRoute>
    @Backlink
    @Json(ignore = true)
    lateinit var busArrivals: ToMany<BusArrival>

    override fun toString(): String {
        return "BusStop(id=$id, busStopCode='$busStopCode', roadName='$roadName', description='$description', latitude='$latitude', longitude='$longitude', busRoutes=$busRoutes, busArrivals=$busArrivals)"
    }
}
