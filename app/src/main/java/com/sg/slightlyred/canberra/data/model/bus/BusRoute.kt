package com.sg.slightlyred.canberra.data.model.bus

import com.sg.slightlyred.canberra.data.db.converter.LocalTimeConverter
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.objectbox.annotation.*
import io.objectbox.relation.ToOne
import java.time.LocalTime

@Entity
@JsonClass(generateAdapter = true)
data class BusRoute(
    @Id
    @Json(ignore = true)
    var id: Long = 0,
    // Key will be made up of BusServiceNo||BusStopCode||StopSequence
    @Index
    @Unique
    @Json(ignore = true)
    var key: String = "",
    @Json(ignore = true)
    var busServiceId: Long = 0,
    @Json(name = "StopSequence")
    var stopSequence: Long = 0,
    @Json(ignore = true)
    var busStopId: Long = 0,
    @Json(name = "Distance")
    var distance: Double = 0.0,
    @Convert(converter = LocalTimeConverter::class, dbType = String::class)
    @Json(name = "WD_FirstBus")
    var weekdayFirstBusTime: LocalTime? = null,
    @Convert(converter = LocalTimeConverter::class, dbType = String::class)
    @Json(name = "WD_LastBus")
    var weekdayLastBusTime: LocalTime? = null,
    @Convert(converter = LocalTimeConverter::class, dbType = String::class)
    @Json(name = "SAT_FirstBus")
    var saturdayFirstBusTime: LocalTime? = null,
    @Convert(converter = LocalTimeConverter::class, dbType = String::class)
    @Json(name = "SAT_LastBus")
    var saturdayLastBusTime: LocalTime? = null,
    @Convert(converter = LocalTimeConverter::class, dbType = String::class)
    @Json(name = "SUN_FirstBus")
    var sundayFirstBusTime: LocalTime? = null,
    @Convert(converter = LocalTimeConverter::class, dbType = String::class)
    @Json(name = "SUN_LastBus")
    var sundayLastBusTime: LocalTime? = null,

    // JSON en/decoing purpose ONLY
    @Json(name = "ServiceNo")
    @Transient
    @Deprecated("JSON en/decoding purpose only, DO NOT USE!")
    var serviceNo: String = "",
    @Json(name = "Operator")
    @Transient
    @Deprecated("JSON en/decoding purpose only, DO NOT USE!")
    var operator: String = "",
    @Json(name = "Direction")
    @Transient
    @Deprecated("JSON en/decoding purpose only, DO NOT USE!")
    var direction: Int = 0,
    @Json(name = "BusStopCode")
    @Transient
    @Deprecated("JSON en/decoding purpose only, DO NOT USE!")
    var busStopCode: String = ""
) {
    @Json(ignore = true)
    lateinit var busService: ToOne<BusService>
    @Json(ignore = true)
    lateinit var busStop: ToOne<BusStop>
}
