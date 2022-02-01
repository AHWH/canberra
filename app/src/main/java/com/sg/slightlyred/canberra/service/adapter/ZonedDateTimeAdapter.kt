package com.sg.slightlyred.canberra.service.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ZonedDateTimeAdapter {
    @ToJson
    fun toJson(zonedDateTime: ZonedDateTime?): String? {
        return zonedDateTime?.format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
    }

    @FromJson
    fun fromJson(zonedDateTimeStr: String?): ZonedDateTime? {
        return if (zonedDateTimeStr != null) ZonedDateTime.parse(zonedDateTimeStr) else null
    }
}