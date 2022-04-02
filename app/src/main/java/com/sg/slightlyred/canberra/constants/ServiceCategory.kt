package com.sg.slightlyred.canberra.constants

import com.squareup.moshi.JsonClass
import java.util.*
import kotlin.collections.HashMap

@JsonClass(generateAdapter = false)
enum class ServiceCategory(val categoryName: String) {
    EXPRESS("Express"),
    FEEDER("Feeder"),
    INDUSTRIAL("Industrial"),
    TOWNLINK("Townlink"),
    TRUNK("Trunk"),
    NIGHT_RIDER("NIGHT RIDER"),
    TWO_TIER_FLAT_FEE("2 Tier Flat Fee"),
    TWO_TIER_FLAT_FARE("2-TIER FLAT FARE"),
    FLAT_FEE_110("Flat Fee, $1.10"),
    FLAT_FEE_190("Flat Fee, $1.90"),
    FLAT_FEE_350("Flat Fee, $3.50"),
    FLAT_FEE_380("Flat Fee, $3.80"),
    CITY_LINK("City Link"),
    SBST("SBST"),
    UNKNOWN("Unknown");

    companion object {
        private lateinit var ENUM_MAP: HashMap<String, ServiceCategory>

        init {
            ENUM_MAP = HashMap()
            for (serviceCategory in values()) {
                ENUM_MAP.put(serviceCategory.categoryName.lowercase(Locale.getDefault()), serviceCategory)
            }
        }

        fun getServiceCategory(serviceCategoryStr: String?): ServiceCategory {
            return ENUM_MAP[serviceCategoryStr?.lowercase()] ?: return UNKNOWN
        }
    }
}