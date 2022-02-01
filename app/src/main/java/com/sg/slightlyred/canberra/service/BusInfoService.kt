package com.sg.slightlyred.canberra.service

import com.sg.slightlyred.canberra.BuildConfig
import com.sg.slightlyred.canberra.data.model.LtaDataMallResponse
import com.sg.slightlyred.canberra.data.model.bus.BusArrival
import com.sg.slightlyred.canberra.data.model.bus.BusRoute
import com.sg.slightlyred.canberra.data.model.bus.BusService
import com.sg.slightlyred.canberra.data.model.bus.BusStop
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BusInfoService {
    @GET("ltaodataservice/BusStops")
    @Headers("AccountKey: " + BuildConfig.LTA_DATAMALL_TOKEN)
    suspend fun getAllBusStops(@Query("\$skip") next: Long): Response<LtaDataMallResponse<BusStop>>

    @GET("ltaodataservice/BusServices")
    @Headers("AccountKey: " + BuildConfig.LTA_DATAMALL_TOKEN)
    suspend fun getAllBusServices(@Query("%24skip") next: Long): Call<List<BusService>>

    @GET("ltaodataservice/BusRoutes")
    @Headers("AccountKey: " + BuildConfig.LTA_DATAMALL_TOKEN)
    suspend fun getAllBusRoutes(@Query("%24skip") next: Long): Call<List<BusRoute>>

    @GET("ltaodataservice/BusArrivalv2")
    @Headers("AccountKey: " + BuildConfig.LTA_DATAMALL_TOKEN)
    suspend fun getBusArrivalsForBusStop(@Query("BusStopCode") busStopCode: String): Call<List<BusArrival>>

    @GET("ltaodataservice/BusArrivalv2")
    @Headers("AccountKey: " + BuildConfig.LTA_DATAMALL_TOKEN)
    suspend fun getBusArrivalsForServiceAtBusStop(@Query("BusStopCode") busStopCode: String, @Query("ServiceNo") busServiceNo: String): Call<List<BusArrival>>
}