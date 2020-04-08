package com.seweryn.dazncodechallenge.di

import android.content.Context
import com.seweryn.dazncodechallenge.data.DaznRepository
import com.seweryn.dazncodechallenge.data.DaznRepositoryImpl
import com.seweryn.dazncodechallenge.data.remote.DaznApi
import com.seweryn.dazncodechallenge.utils.DateFormatter
import com.seweryn.dazncodechallenge.utils.DateFormatterImpl
import com.seweryn.dazncodechallenge.utils.StringProvider
import com.seweryn.dazncodechallenge.utils.StringProviderImpl
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val context: Context) {

    @Provides
    fun provideStringProvider(): StringProvider = StringProviderImpl(context.resources)

    @Provides
    fun provideDateFormatter(stringProvider: StringProvider): DateFormatter = DateFormatterImpl(stringProvider)

    @Provides
    fun provideDaznRepository(daznApi: DaznApi): DaznRepository = DaznRepositoryImpl(daznApi)
}