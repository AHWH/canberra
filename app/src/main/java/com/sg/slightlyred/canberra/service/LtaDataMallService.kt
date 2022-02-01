package com.sg.slightlyred.canberra.service

import com.sg.slightlyred.canberra.service.adapter.LocalTimeAdapter
import com.sg.slightlyred.canberra.service.adapter.ZonedDateTimeAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface LtaDataMallService {
    companion object {
        private const val BASE_URL: String = "http://datamall2.mytransport.sg/"
        var ltaDataMallRetrofit: Retrofit? = null

        fun getInstance(): Retrofit {
            if (ltaDataMallRetrofit == null) {
                val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
                logger.redactHeader("AccountKey")
                logger.redactHeader("Cookie")
                val client = OkHttpClient.Builder().addInterceptor(logger).build()
                val moshi: Moshi = Moshi.Builder()
                    .add(LocalTimeAdapter())
                    .add(ZonedDateTimeAdapter())
                    .addLast(KotlinJsonAdapterFactory())
                    .build()

                ltaDataMallRetrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
            }
            return ltaDataMallRetrofit!!
        }
    }
}