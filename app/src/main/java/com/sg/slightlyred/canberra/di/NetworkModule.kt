package com.sg.slightlyred.canberra.di

import com.sg.slightlyred.canberra.service.BusInfoRemoteSource
import com.sg.slightlyred.canberra.service.BusInfoService
import com.sg.slightlyred.canberra.service.LtaDataMallService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideLtaDataMallRetrofit(): Retrofit = LtaDataMallService.getInstance()

    @Singleton
    @Provides
    fun provideBusInfoService(retrofit: Retrofit): BusInfoService = retrofit.create(BusInfoService::class.java)

    @Singleton
    @Provides
    fun provideBusInfoRemoteSource(busInfoService: BusInfoService): BusInfoRemoteSource = BusInfoRemoteSource(busInfoService)
}