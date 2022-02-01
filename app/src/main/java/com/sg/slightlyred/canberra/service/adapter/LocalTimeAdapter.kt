package com.sg.slightlyred.canberra.service.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalTime

class LocalTimeAdapter {
    @ToJson fun toJson(localTime: LocalTime?): String? {
        return localTime?.toString()
    }

    @FromJson fun fromJson(localTimeStr: String?): LocalTime? {
        return if (localTimeStr != null) LocalTime.parse(localTimeStr) else null
    }
}