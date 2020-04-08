package com.seweryn.dazncodechallenge.di

import com.seweryn.dazncodechallenge.data.remote.DaznApi
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
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Named("dazn")
    fun provideDAZNApiClient(okHttpClient: OkHttpClient): Retrofit {
        return buildRetrofitClient(okHttpClient, "https://us-central1-dazn-sandbox.cloudfunctions.net")
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