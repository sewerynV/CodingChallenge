package com.seweryn.dazncodechallenge.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.seweryn.dazncodechallenge.data.DaznRepository
import com.seweryn.dazncodechallenge.data.DaznRepositoryImpl
import com.seweryn.dazncodechallenge.data.local.Database
import com.seweryn.dazncodechallenge.data.remote.DaznApi
import com.seweryn.dazncodechallenge.utils.*
import com.seweryn.dazncodechallenge.utils.network.ConnectionManager
import com.seweryn.dazncodechallenge.utils.network.ConnectionManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    fun provideStringProvider(): StringProvider = StringProviderImpl(application.resources)

    @Provides
    fun provideDateFormatter(stringProvider: StringProvider): DateFormatter = DateFormatterImpl(stringProvider)

    @Provides
    fun provideConnectionManager(): ConnectionManager = ConnectionManagerImpl(application)

    @Provides
    fun provideSchedulersProvider(): SchedulersProvider = SchedulerProviderImpl()

    @Provides
    fun provideDaznRepository(daznApi: DaznApi, database: Database): DaznRepository = DaznRepositoryImpl(daznApi, database)

    @Provides
    @Singleton
    fun provideDaznDatabase(): Database = Room.databaseBuilder(application,
        Database::class.java, Constants.DAZN_DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()
}