package com.seweryn.dazncodechallenge.di

import com.seweryn.dazncodechallenge.data.remote.DaznApi
import com.seweryn.dazncodechallenge.tools.network.NetworkConnectionInterceptor
import com.seweryn.dazncodechallenge.utils.Constants
import com.seweryn.dazncodechallenge.utils.network.ConnectionManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class NetworkModule {

    @Provides
    fun provideOkHttpClient(connectionManager: ConnectionManager): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(NetworkConnectionInterceptor(connectionManager))
        .build()

    @Provides
    @Named("dazn")
    fun provideDAZNApiClient(okHttpClient: OkHttpClient): Retrofit {
        return buildRetrofitClient(
            okHttpClient,
            Constants.DAZN_BASE_URL
        )
    }

    @Provides
    fun provideDAZNApiInterface(@Named("dazn") retrofit: Retrofit): DaznApi {
        return retrofit.create(DaznApi::class.java)
    }

    private fun buildRetrofitClient(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Builder().client(okHttpClient).baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}