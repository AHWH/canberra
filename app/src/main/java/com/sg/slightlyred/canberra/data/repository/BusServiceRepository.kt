package com.sg.slightlyred.canberra.data.repository

import com.sg.slightlyred.canberra.data.db.dao.BusServiceDao
import com.sg.slightlyred.canberra.data.model.bus.BusService
import com.sg.slightlyred.canberra.service.BusInfoRemoteSource
import com.sg.slightlyred.canberra.utils.DataAccessStrategy
import com.sg.slightlyred.canberra.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BusServiceRepository @Inject constructor(
    private val busInfoRemoteSource: BusInfoRemoteSource,
    private val busServiceDao: BusServiceDao
) {
    suspend fun getAllBusServices(): Flow<ResponseState<List<BusService>>> = DataAccessStrategy.performGet(
        { busServiceDao.getAll() },
        { busInfoRemoteSource.getAllBusServices() },
        { busServiceDao.add(it) }
    )

    suspend fun getAllBusServicesRemoteOnly(): Flow<ResponseState<List<BusService>>> = DataAccessStrategy.performGet(
        null,
        { busInfoRemoteSource.getAllBusServices() },
        { busServiceDao.add(it) }
    )

    suspend fun getAllBusServicesLocalOnly(): Flow<ResponseState<List<BusService>>> = DataAccessStrategy.performGet(
        { busServiceDao.getAll() },
        null,
        null
    )

    suspend fun getAllBusServicesRemoteAndSaveOnly(): Flow<ResponseState<Boolean>> = DataAccessStrategy.performGetRemoteAndSaveOnly(
        { busInfoRemoteSource.getAllBusServices() },
        { busServiceDao.addWithStatus(it) }
    )
}