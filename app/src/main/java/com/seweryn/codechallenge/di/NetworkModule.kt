package com.seweryn.codechallenge.di

import com.seweryn.codechallenge.data.remote.EventsApi
import com.seweryn.codechallenge.tools.network.NetworkConnectionInterceptor
import com.seweryn.codechallenge.utils.Constants
import com.seweryn.codechallenge.utils.network.ConnectionManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(connectionManager: ConnectionManager): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(NetworkConnectionInterceptor(connectionManager))
        .build()

    @Provides
    @Singleton
    @Named("events")
    fun provideEventsApiClient(okHttpClient: OkHttpClient): Retrofit {
        return buildRetrofitClient(
            okHttpClient,
            Constants.EVENTS_BASE_URL
        )
    }

    @Provides
    @Singleton
    fun provideEventsApiInterface(@Named("events") retrofit: Retrofit): EventsApi {
        return retrofit.create(EventsApi::class.java)
    }

    private fun buildRetrofitClient(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Builder().client(okHttpClient).baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}