package com.sg.slightlyred.canberra.constants

import android.Manifest

object AppConstants {
    @JvmStatic
    val LOCATION_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    @JvmStatic
    val LOCATION_CANCELLTATION_TOKEN = "CANCEL_LOCATION_REQUEST";
    @JvmStatic
    val ERROR_FAILED_TO_GET_LOCATION = "Failed to get Location"
    @JvmStatic
    val ERROR_FAILED_TO_GET_LOCATION_PERMISSION = "Failed to get Location due to missing permission"
    @JvmStatic
    val DEFAULT_ZOOM_VALUE: Double = 15.0
    @JvmStatic
    val DEPRECATED_MSG_JSON_ONLY = "JSON en/decoding purpose only, DO NOT USE!"

    @JvmStatic
    val LTA_DATAMALL_MAX_VALUES_LENGTH = 500

    @JvmStatic
    val APP_MAP_CAMERA_CENTER: String = "APP_MAP_CAMERA_CENTER"
}