package com.sg.slightlyred.canberra.livedata.location

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.sg.slightlyred.canberra.data.model.LocationResponse
import javax.inject.Inject

abstract class LocationLiveData() : MutableLiveData<LocationResponse>() {
    fun setLocationData(locationResponse: LocationResponse) {
        value = locationResponse
    }

    abstract fun requestLocation()
}