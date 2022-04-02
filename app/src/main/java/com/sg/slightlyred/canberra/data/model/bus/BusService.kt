package com.sg.slightlyred.canberra.data.model.bus

import com.sg.slightlyred.canberra.constants.ServiceCategory
import com.sg.slightlyred.canberra.constants.ServiceOperator
import com.sg.slightlyred.canberra.data.db.converter.ServiceCategoryConverter
import com.sg.slightlyred.canberra.data.db.converter.ServiceOperatorConverter
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.objectbox.annotation.*
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
@JsonClass(generateAdapter = true)
data class BusService(
    @Id
    @Json(ignore = true)
    var id: Long = 0,
    @Index
    @Json(name = "ServiceNo")
    var serviceNo: String = "",
    @Convert(converter = ServiceOperatorConverter::class, dbType = String::class)
    @Json(name = "Operator")
    var operator: ServiceOperator? = null,
    @Index
    @Json(name = "Direction")
    var direction: Int = 0,
    @Convert(converter = ServiceCategoryConverter::class, dbType = String::class)
    @Json(name = "Category")
    var category: ServiceCategory? = null,
    @Json(ignore = true)
    var originBusStopId: Long = 0,
    @Json(ignore = true)
    var destinationBusStopId: Long = 0,
    @Json(name = "AM_Peak_Freq")
    var amPeakFrequency: String = "",
    @Json(name = "AM_Offpeak_Freq")
    var amOffPeakFrequency: String = "",
    @Json(name = "PM_Peak_Freq")
    var pmPeakFrequency: String = "",
    @Json(name = "PM_Offpeak_Freq")
    var pmOffPeakFrequency: String = "",
    @Json(name = "LoopDesc")
    var loopLocationDesc: String = "",

    // For JSON En/Decoding purpose ONLY
    @Json(name = "OriginCode")
    @Transient
    @Deprecated("JSON en/decoding purpose only, DO NOT USE!")
    var originBusStopCode: String = "",
    @Json(name = "DestinationCode")
    @Transient
    @Deprecated("JSON en/decoding purpose only, DO NOT USE!")
    var destinationBusStopCode: String = ""
) {
    @Json(ignore = true)
    lateinit var originBusStop: ToOne<BusStop>
    @Json(ignore = true)
    lateinit var destinationBusStop: ToOne<BusStop>
    @Backlink
    @Json(ignore = true)
    lateinit var busRoutes: ToMany<BusRoute>
}