package com.sg.slightlyred.canberra.data.repository

import com.sg.slightlyred.canberra.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class BusInfoRepository @Inject constructor(
    private val busStopRepository: BusStopRepository,
    private val busServiceRepository: BusServiceRepository,
    private val busRouteRepository: BusRouteRepository
) {
    suspend fun getAllEssentialBusInfoRemoteAndSaveOnly(): Flow<ResponseState<Boolean>> = flow {
        busStopRepository.getAllBusStopsRemoteAndSaveOnly()
            .transform { it ->
                when (it) {
                    is ResponseState.Success -> busServiceRepository.getAllBusServicesRemoteAndSaveOnly().collect { emit(it) }
                    else -> {}
                }
            }
            .transform { it ->
                when (it) {
                    is ResponseState.Success -> busRouteRepository.getAllBusRouteRemoteAndSaveOnly().collect { emit(it) }
                    else -> {}
                }
            }
            .collect { emit(it) }
    }
}