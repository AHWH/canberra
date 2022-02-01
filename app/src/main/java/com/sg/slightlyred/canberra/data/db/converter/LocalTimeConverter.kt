package com.sg.slightlyred.canberra.data.db.converter

import io.objectbox.converter.PropertyConverter
import java.time.LocalTime

class LocalTimeConverter : PropertyConverter<LocalTime?, String?> {
    override fun convertToEntityProperty(databaseValue: String?): LocalTime? {
        return if (databaseValue != null) LocalTime.parse(databaseValue) else null
    }

    override fun convertToDatabaseValue(entityProperty: LocalTime?): String? {
        return entityProperty?.toString()
    }
}