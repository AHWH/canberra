package com.sg.slightlyred.canberra.di

import android.content.Context
import com.sg.slightlyred.canberra.data.db.dao.AppPreferencesDao
import com.sg.slightlyred.canberra.data.db.dao.BusRouteDao
import com.sg.slightlyred.canberra.data.db.dao.BusServiceDao
import com.sg.slightlyred.canberra.data.db.dao.BusStopDao
import com.sg.slightlyred.canberra.data.model.bus.BusRoute
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppPreferencesDao(@ApplicationContext context: Context): AppPreferencesDao = AppPreferencesDao(context)

    @Singleton
    @Provides
    fun provideBusStopDao(): BusStopDao = BusStopDao()

    @Singleton
    @Provides
    fun provideBusServiceDao(): BusServiceDao = BusServiceDao()

    @Singleton
    @Provides
    fun provideBusRouteDao(): BusRouteDao = BusRouteDao()
}