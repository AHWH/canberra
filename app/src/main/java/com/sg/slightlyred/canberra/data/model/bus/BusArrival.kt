package com.sg.slightlyred.canberra.data.model.bus

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.objectbox.annotation.*
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
@JsonClass(generateAdapter = true)
data class BusArrival(
    @Id
    @Json(ignore = true)
    var id: Long = 0,
    // Key will be busService||busStop
    @Index
    @Unique
    @Json(ignore = true)
    var busArrivalKey: String = "",
    @Json(ignore = true)
    var busServiceId: Long = 0,
    @Json(ignore = true)
    var busStopId: Long = 0,

    // For JSON En/Decoding purpose ONLY
    @Json(name = "ServiceNo")
    @Transient
    @Deprecated("JSON en/decoding purpose only, DO NOT USE!")
    var serviceNo: String = "",
    @Json(name = "Operator")
    @Transient
    @Deprecated("JSON en/decoding purpose only, DO NOT USE!")
    var operator: String = "",
    @Json(name = "NextBus")
    @Transient
    @Deprecated("JSON en/decoding purpose only, DO NOT USE!")
    var nextBus: NextBus? = null,
    @Json(name = "NextBus2")
    @Transient
    @Deprecated("JSON en/decoding purpose only, DO NOT USE!")
    var nextBus2: NextBus? = null,
    @Json(name = "NextBus3")
    @Transient
    @Deprecated("JSON en/decoding purpose only, DO NOT USE!")
    var nextBus3: NextBus? = null,
) {
    @Json(ignore = true)
    lateinit var busService: ToOne<BusService>
    @Json(ignore = true)
    lateinit var busStop: ToOne<BusStop>
    @Backlink
    @Json(ignore = true)
    lateinit var nextBusArrivals: ToMany<NextBus>
}
