package com.sg.slightlyred.canberra.di

import android.app.Application
import com.google.android.gms.location.LocationServices
import com.sg.slightlyred.canberra.data.db.dao.AppPreferencesDao
import com.sg.slightlyred.canberra.data.db.dao.BusStopDao
import com.sg.slightlyred.canberra.data.repository.AppPreferencesRepository
import com.sg.slightlyred.canberra.data.repository.BusStopRepository
import com.sg.slightlyred.canberra.service.BusInfoRemoteSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideLocationProviderClient(app: Application) = LocationServices.getFusedLocationProviderClient(app)

    @Singleton
    @Provides
    fun provideBusStopRepository(remoteDataSource: BusInfoRemoteSource, localDataSource: BusStopDao) = BusStopRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideAppPreferencesRepository(localDataSource: AppPreferencesDao) = AppPreferencesRepository(localDataSource)

}