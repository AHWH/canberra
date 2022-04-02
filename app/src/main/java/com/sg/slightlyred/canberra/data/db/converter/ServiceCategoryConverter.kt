package com.sg.slightlyred.canberra.data.db.converter

import com.sg.slightlyred.canberra.constants.ServiceCategory
import io.objectbox.converter.PropertyConverter

class ServiceCategoryConverter : PropertyConverter<ServiceCategory?, String?> {
    override fun convertToEntityProperty(databaseValue: String?): ServiceCategory? {
        if (databaseValue == null) {
            return null
        }
        return ServiceCategory.getServiceCategory(databaseValue)
    }

    override fun convertToDatabaseValue(entityProperty: ServiceCategory?): String? {
        return entityProperty?.name
    }
}