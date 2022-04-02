package com.sg.slightlyred.canberra.service.adapter

import com.sg.slightlyred.canberra.constants.ServiceCategory
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class ServiceCategoryAdapter {

    @ToJson
    fun toJson(serviceCategory: ServiceCategory?): String? {
        return serviceCategory?.categoryName
    }

    @FromJson
    fun fromJson(serviceCategoryStr: String?): ServiceCategory {
        return ServiceCategory.getServiceCategory(serviceCategoryStr)
    }
}