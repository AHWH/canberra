package com.sg.slightlyred.canberra.view.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sg.slightlyred.canberra.data.repository.BusStopRepository
import com.sg.slightlyred.canberra.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(private val busStopRepository: BusStopRepository): ViewModel() {
    private val _getBusStopsAndSaveStatus = MutableStateFlow<ResponseState<Boolean>>(ResponseState.Loading())
    val getBusStopsAndSaveStatus: StateFlow<ResponseState<Boolean>> = _getBusStopsAndSaveStatus

    fun getAndSaveAllBusStopsOnly() {
        viewModelScope.launch {
            busStopRepository.getAllBusStopsRemoteAndSaveOnly().collect {
                _getBusStopsAndSaveStatus.value = it
            }
        }
    }
}