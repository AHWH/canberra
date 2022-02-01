package com.sg.slightlyred.canberra.view.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.CancellationTokenSource
import com.sg.slightlyred.canberra.data.model.LocationResponse
import com.sg.slightlyred.canberra.livedata.location.LocationLiveData
import com.sg.slightlyred.canberra.livedata.location.google.ManualLocationLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapboxFragmentViewModel @Inject constructor(private val fusedLocationProviderClient: FusedLocationProviderClient): ViewModel() {
    private val locationTaskCancellationTokenSource: CancellationTokenSource = CancellationTokenSource()

    private val locationData: MutableLiveData<LocationResponse> by lazy {
        ManualLocationLiveData(fusedLocationProviderClient, locationTaskCancellationTokenSource)
    }

    fun getLocation(): LiveData<LocationResponse> {
        return locationData
    }

    fun requestLocation() {
        (locationData as LocationLiveData).requestLocation()
    }
}