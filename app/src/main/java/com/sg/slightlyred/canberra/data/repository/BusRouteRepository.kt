package com.sg.slightlyred.canberra.data.repository

import com.sg.slightlyred.canberra.data.db.dao.BusRouteDao
import com.sg.slightlyred.canberra.data.model.bus.BusRoute
import com.sg.slightlyred.canberra.service.BusInfoRemoteSource
import com.sg.slightlyred.canberra.utils.DataAccessStrategy
import com.sg.slightlyred.canberra.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BusRouteRepository @Inject constructor(
    private val busInfoRemoteSource: BusInfoRemoteSource,
    private val busRouteDao: BusRouteDao
) {
    suspend fun getAllBusRoutes(): Flow<ResponseState<List<BusRoute>>> = DataAccessStrategy.performGet(
        { busRouteDao.getAll() },
        { busInfoRemoteSource.getAllBusRoutes() },
        { busRouteDao.add(it) }
    )

    suspend fun getAllBusRoutesRemoteOnly(): Flow<ResponseState<List<BusRoute>>> = DataAccessStrategy.performGet(
        null,
        { busInfoRemoteSource.getAllBusRoutes() },
        { busRouteDao.add(it) }
    )

    suspend fun getAllBusRoutesLocalOnly(): Flow<ResponseState<List<BusRoute>>> = DataAccessStrategy.performGet(
        { busRouteDao.getAll() },
        null,
        null
    )

    suspend fun getAllBusRouteRemoteAndSaveOnly(): Flow<ResponseState<Boolean>> = DataAccessStrategy.performGetRemoteAndSaveOnly(
        { busInfoRemoteSource.getAllBusRoutes() },
        { busRouteDao.addWithStatus(it) }
    )
}