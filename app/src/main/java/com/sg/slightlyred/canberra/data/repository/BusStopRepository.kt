package com.sg.slightlyred.canberra.data.repository

import com.sg.slightlyred.canberra.data.db.dao.BusStopDao
import com.sg.slightlyred.canberra.data.model.bus.BusStop
import com.sg.slightlyred.canberra.service.BusInfoRemoteSource
import com.sg.slightlyred.canberra.utils.DataAccessStrategy
import com.sg.slightlyred.canberra.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BusStopRepository @Inject constructor(
    private val busInfoRemoteSource: BusInfoRemoteSource,
    private val busStopDao: BusStopDao
) {

    suspend fun getAllBusStops(): Flow<ResponseState<List<BusStop>>> = DataAccessStrategy.performGet(
        { busStopDao.getAll() },
        { busInfoRemoteSource.getAllBusStops() },
        { busStopDao.add(it) }
    )

    suspend fun getAllBusStopsRemoteOnly(): Flow<ResponseState<List<BusStop>>> = DataAccessStrategy.performGet(
        null,
        { busInfoRemoteSource.getAllBusStops() },
        { busStopDao.add(it) }
    )

    suspend fun getAllBusStopsRemoteAndSaveOnly(): Flow<ResponseState<Boolean>> = DataAccessStrategy.performGetRemoteAndSaveOnly(
        { busInfoRemoteSource.getAllBusStops() },
        { busStopDao.addWithStatus(it) }
    )

    suspend fun getAllBusStopsLocalOnly(): Flow<ResponseState<List<BusStop>>> = DataAccessStrategy.performGet(
        { busStopDao.getAll() },
        null,
        { busStopDao.add(it) }
    )
}