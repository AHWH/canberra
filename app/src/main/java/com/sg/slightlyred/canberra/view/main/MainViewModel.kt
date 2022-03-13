package com.sg.slightlyred.canberra.view.main;

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grum.geocalc.BoundingArea
import com.grum.geocalc.EarthCalc
import com.sg.slightlyred.canberra.data.model.bus.BusStop
import com.sg.slightlyred.canberra.data.repository.BusStopRepository
import com.sg.slightlyred.canberra.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val busStopRepository: BusStopRepository) : ViewModel() {
    private val TAG: String = "MainViewModel"

    private val _currentLocation: MutableStateFlow<ResponseState<Location>> = MutableStateFlow(ResponseState.Loading())
    val currentLocation: StateFlow<ResponseState<Location>> get() = _currentLocation

    private val _allBusStops = MutableStateFlow<ResponseState<List<BusStop>>>(ResponseState.Loading())
    val allBusStop: StateFlow<ResponseState<List<BusStop>>> = _allBusStops

    private val _nearbyBusStops = MutableStateFlow<ResponseState<List<BusStop>>>(ResponseState.Loading())
    val nearbyBusStops: StateFlow<ResponseState<List<BusStop>>> = _nearbyBusStops

    fun updateLocation(location: Location) {
        _currentLocation.value = ResponseState.Success(location)
    }

    fun fetchAllBusStops() {
        Log.i(TAG, "Fetching all bus stops")
        viewModelScope.launch {
            busStopRepository.getAllBusStopsLocalOnly().collect {
                _allBusStops.value = it
            }
        }
    }

    fun updateNearbyBusStops(nearbyBusStops: List<BusStop>) {
        _nearbyBusStops.value = ResponseState.Success(nearbyBusStops)
    }
}
