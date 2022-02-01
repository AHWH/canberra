package com.sg.slightlyred.canberra.view.setup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sg.slightlyred.canberra.data.model.bus.BusStop
import com.sg.slightlyred.canberra.data.repository.BusStopRepository
import com.sg.slightlyred.canberra.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(private val busStopRepository: BusStopRepository): ViewModel() {
    private val _allBusStops = MutableStateFlow<ResponseState<List<BusStop>>>(ResponseState.Loading())
    val allBusStop: StateFlow<ResponseState<List<BusStop>>> = _allBusStops

    fun fetchAllBusStops() {
        Log.i("SetupViewModel", "Fetching all bus stops")
        viewModelScope.launch {
            busStopRepository.getAllBusStopsRemoteOnly().collect {
                _allBusStops.value = it
            }
        }
    }
}