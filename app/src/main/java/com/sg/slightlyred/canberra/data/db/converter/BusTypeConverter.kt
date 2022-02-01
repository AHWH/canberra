package com.sg.slightlyred.canberra.data.db.converter

import com.sg.slightlyred.canberra.constants.BusType
import io.objectbox.converter.PropertyConverter
import java.lang.IllegalArgumentException

class BusTypeConverter : PropertyConverter<BusType?, String?> {
    override fun convertToEntityProperty(databaseValue: String?): BusType? {
        if (databaseValue == null) {
            return null
        }
        return try {
            BusType.valueOf(databaseValue)
        } catch (ex: IllegalArgumentException) {
            BusType.UNKNOWN
        }
    }

    override fun convertToDatabaseValue(entityProperty: BusType?): String? {
        return entityProperty?.name
    }
}