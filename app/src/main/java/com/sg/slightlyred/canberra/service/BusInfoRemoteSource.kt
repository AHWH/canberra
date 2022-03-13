package com.sg.slightlyred.canberra.service

import android.util.Log
import com.sg.slightlyred.canberra.constants.AppConstants
import com.sg.slightlyred.canberra.data.model.LtaDataMallResponse
import com.sg.slightlyred.canberra.data.model.bus.BusService
import com.sg.slightlyred.canberra.data.model.bus.BusStop
import com.sg.slightlyred.canberra.utils.ResponseState
import java.util.*
import javax.inject.Inject

class BusInfoRemoteSource @Inject constructor(
    private val busInfoService: BusInfoService
) : RemoteSource() {
    private val TAG: String = "BusInfoRemoteSource"

    suspend fun getAllBusStops(): ResponseState<List<BusStop>> {
        // Current max is 5063
        // Each response respond 500 count
        var isAllBusStopQueried: Boolean = false
        val allBusStops: LinkedList<BusStop> = LinkedList()
        var errorMessage: String? = null
        var skip: Long = 0

        while (!isAllBusStopQueried) {
            Log.i(TAG, "getAllBusStops() :: Getting next $skip results")
            val busStopQueryResponseState: ResponseState<LtaDataMallResponse<BusStop>> = getResult { busInfoService.getAllBusStops(skip) }

            when (busStopQueryResponseState) {
                is ResponseState.Success -> {
                    val busStops: List<BusStop> = busStopQueryResponseState.data!!.values
                    allBusStops.addAll(busStops)

                    if (busStops.size < AppConstants.LTA_DATAMALL_MAX_VALUES_LENGTH) {
                        isAllBusStopQueried = true
                    } else {
                        skip += AppConstants.LTA_DATAMALL_MAX_VALUES_LENGTH
                    }
                }
                is ResponseState.Error -> {
                    errorMessage = busStopQueryResponseState.message!!
                    Log.e(TAG, "getAllBusStops() :: Error received. $errorMessage")
                    isAllBusStopQueried = true
                }
                else -> {
                    isAllBusStopQueried = true
                    Log.e(TAG, "getAllBusStops() :: Invalid NetworkResponse status received!")
                }
            }
        }

        return if (errorMessage == null) ResponseState.Success(allBusStops) else ResponseState.Error(null, errorMessage)
    }

    suspend fun getAllBusServices(): ResponseState<List<BusService>> {
        // Current max is ???
        // Each response returns max 500 counts
        var isAllBusServicesQueried: Boolean = false;
        var allBusServices: LinkedList<BusService> = LinkedList()
        var errorMessage: String? = null
        var skip: Long = 0

        while (!isAllBusServicesQueried) {
            Log.i(TAG, "getAllBusServices() :: Getting next $skip results")
            val busServiceQueryResponseState: ResponseState<LtaDataMallResponse<BusService>> = getResult { busInfoService.getAllBusServices(skip) }

            when (busServiceQueryResponseState) {
                is ResponseState.Success -> {
                    val busServices: List<BusService> = busServiceQueryResponseState.data!!.values
                    allBusServices.addAll(busServices)

                    if (busServices.size < AppConstants.LTA_DATAMALL_MAX_VALUES_LENGTH) {
                        isAllBusServicesQueried = true
                    } else {
                        skip += AppConstants.LTA_DATAMALL_MAX_VALUES_LENGTH
                    }
                }
                is ResponseState.Error -> {
                    errorMessage = busServiceQueryResponseState.message!!
                    Log.e(TAG, "getAllBusServices() :: Error Received. $errorMessage")
                    isAllBusServicesQueried = true
                }
                else -> {
                    isAllBusServicesQueried = true
                    Log.e(TAG, "getAllBusServices() :: Invalid NetworkResponse status received!")
                }
            }
        }

        return if (errorMessage == null) ResponseState.Success(allBusServices) else ResponseState.Error(null, errorMessage)
    }
}