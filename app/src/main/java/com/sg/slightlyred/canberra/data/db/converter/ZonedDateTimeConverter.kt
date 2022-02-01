package com.sg.slightlyred.canberra.data.db.converter

import io.objectbox.converter.PropertyConverter
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ZonedDateTimeConverter : PropertyConverter<ZonedDateTime?, String?> {

    override fun convertToEntityProperty(databaseValue: String?): ZonedDateTime? {
        return if (databaseValue != null) ZonedDateTime.parse(databaseValue) else null
    }

    override fun convertToDatabaseValue(entityProperty: ZonedDateTime?): String? {
        return entityProperty?.format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
    }
}