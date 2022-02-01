package com.sg.slightlyred.canberra.data.db.converter

import com.sg.slightlyred.canberra.constants.ServiceOperator
import io.objectbox.converter.PropertyConverter

class ServiceOperatorConverter : PropertyConverter<ServiceOperator?, String?> {
    override fun convertToEntityProperty(databaseValue: String?): ServiceOperator? {
        if (databaseValue == null) {
            return null
        }
        return try {
            ServiceOperator.valueOf(databaseValue)
        } catch (ex: IllegalArgumentException) {
            ServiceOperator.UNKNOWN
        }
    }

    override fun convertToDatabaseValue(entityProperty: ServiceOperator?): String? {
        return entityProperty?.name
    }
}