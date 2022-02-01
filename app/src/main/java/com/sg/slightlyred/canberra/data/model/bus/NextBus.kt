package com.sg.slightlyred.canberra.data.model.bus

import com.sg.slightlyred.canberra.constants.BusLoad
import com.sg.slightlyred.canberra.constants.BusType
import com.sg.slightlyred.canberra.data.db.converter.BusLoadConverter
import com.sg.slightlyred.canberra.data.db.converter.BusTypeConverter
import com.sg.slightlyred.canberra.data.db.converter.ZonedDateTimeConverter
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient
import io.objectbox.relation.ToOne
import java.time.ZonedDateTime

@Entity
@JsonClass(generateAdapter = true)
data class NextBus(
    @Id
    @Json(ignore = true)
    var id: Long = 0,
    @Json(ignore = true)
    var busServiceId: Long = 0,
    @Json(ignore = true)
    var busStopId: Long = 0,
    @Json(ignore = true)
    var originBusStopId: Long = 0,
    @Json(ignore = true)
    var destinationBusStopId: Long = 0,
    @Convert(converter = ZonedDateTimeConverter::class, dbType = String::class)
    @Json(name = "EstimatedArrival")
    var estimatedArrivalDateTime: ZonedDateTime? = null,
    @Json(name = "Latitude")
    var currentLatitude: String = "",
    @Json(name = "Longitude")
    var currentLongitude: String = "",
    @Json(name = "VisitNumber")
    var visitTimes: Int = 0,
    @Convert(converter = BusLoadConverter::class, dbType = String::class)
    @Json(name = "Load")
    var busLoad: BusLoad? = null,
    @Json(ignore = true)
    var isAccessible: Boolean = false,
    @Convert(converter = BusTypeConverter::class, dbType = String::class)
    @Json(name = "Type")
    var busType: BusType? = null,
    @Convert(converter = ZonedDateTimeConverter::class, dbType = String::class)
    @Json(ignore = true)
    var createdDateTime: ZonedDateTime? = null,
    @Json(ignore = true)
    var busArrivalId: Long = 0,

    // For JSON En/Decoding purpose ONLY
    @Json(name = "OriginCode")
    @Transient
    @Deprecated("JSON en/decoding purpose only, DO NOT USE!")
    var originBusStopCode: String = "",
    @Json(name = "DestinationCode")
    @Transient
    @Deprecated("JSON en/decoding purpose only, DO NOT USE!")
    var destinationBusStopCode: String = "",
    @Json(name = "Feature")
    @Transient
    @Deprecated("JSON en/decoding purpose only, DO NOT USE!")
    var feature: String = ""
) {
    @Json(ignore = true)
    lateinit var busService: ToOne<BusService>
    @Json(ignore = true)
    lateinit var busStop: ToOne<BusStop>
    @Json(ignore = true)
    lateinit var busArrival: ToOne<BusArrival>
}
