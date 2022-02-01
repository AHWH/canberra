package com.sg.slightlyred.canberra.livedata.location.google

import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnCanceledListener
import com.google.android.gms.tasks.OnSuccessListener
import com.sg.slightlyred.canberra.constants.AppConstants
import com.sg.slightlyred.canberra.data.model.LocationResponse
import com.sg.slightlyred.canberra.livedata.location.LocationLiveData

class ManualLocationLiveData(private val fusedLocationProviderClient: FusedLocationProviderClient,
                             private val cancellationTokenSource: CancellationTokenSource) : LocationLiveData(), OnSuccessListener<Location>, OnCanceledListener {
    private val LOG_TAG: String = ManualLocationLiveData::class.java.name

    private fun getCurrentLocation(cancellationToken: CancellationToken) {
        try {
            fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, cancellationToken).addOnCanceledListener(this).addOnSuccessListener(this)
        } catch (securityException: SecurityException) {
            setLocationData(LocationResponse(null, AppConstants.ERROR_FAILED_TO_GET_LOCATION_PERMISSION))
        }
    }

    override fun onCanceled() {
        Log.i(LOG_TAG, "Task to get location has been cancelled")
    }

    override fun onSuccess(location: Location?) {
        val locationResponse: LocationResponse = if (location == null) {
            LocationResponse(null, AppConstants.ERROR_FAILED_TO_GET_LOCATION)
        } else {
            LocationResponse(location)
        }

        setLocationData(locationResponse)
    }

    override fun requestLocation() {
        getCurrentLocation(cancellationTokenSource.token)
    }
}