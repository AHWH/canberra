package com.sg.slightlyred.canberra.data.db.converter

import com.sg.slightlyred.canberra.constants.BusLoad
import io.objectbox.converter.PropertyConverter
import java.lang.IllegalArgumentException

class BusLoadConverter : PropertyConverter<BusLoad?, String?> {
    override fun convertToEntityProperty(databaseValue: String?): BusLoad? {
        if (databaseValue == null) {
            return null
        }
        return try {
            BusLoad.valueOf(databaseValue)
        } catch (ex: IllegalArgumentException) {
            return BusLoad.UNKNOWN
        }
    }

    override fun convertToDatabaseValue(entityProperty: BusLoad?): String? {
        return entityProperty?.name
    }
}