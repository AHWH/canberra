package com.sg.slightlyred.canberra.service.adapter

import com.sg.slightlyred.canberra.constants.AppConstants.LTA_DATAMALL_NOT_APPLICABLE_DASH
import com.sg.slightlyred.canberra.constants.AppConstants.LTA_DATAMALL_TIME_FORMATTER
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalTimeAdapter {
    @ToJson fun toJson(localTime: LocalTime?): String? {
        return localTime?.toString()
    }

    @FromJson fun fromJson(localTimeStr: String?): LocalTime? {
        return if (localTimeStr != null && localTimeStr != LTA_DATAMALL_NOT_APPLICABLE_DASH) LocalTime.parse(localTimeStr, LTA_DATAMALL_TIME_FORMATTER) else null
    }
}