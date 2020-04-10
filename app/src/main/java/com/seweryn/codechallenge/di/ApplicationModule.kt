package com.seweryn.codechallenge.di

import android.app.Application
import androidx.room.Room
import com.seweryn.codechallenge.data.EventsRepository
import com.seweryn.codechallenge.data.EventsRepositoryImpl
import com.seweryn.codechallenge.data.local.Database
import com.seweryn.codechallenge.data.remote.EventsApi
import com.seweryn.codechallenge.utils.*
import com.seweryn.codechallenge.utils.network.ConnectionManager
import com.seweryn.codechallenge.utils.network.ConnectionManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideStringProvider(): StringProvider = StringProviderImpl(application.resources)

    @Provides
    @Singleton
    fun provideDateFormatter(stringProvider: StringProvider): DateFormatter =
        DateFormatterImpl(stringProvider)

    @Provides
    @Singleton
    fun provideConnectionManager(): ConnectionManager = ConnectionManagerImpl(application)

    @Provides
    @Singleton
    fun provideSchedulersProvider(): SchedulersProvider = SchedulerProviderImpl()

    @Provides
    @Singleton
    fun provideEventsRepository(eventsApi: EventsApi, database: Database): EventsRepository =
        EventsRepositoryImpl(eventsApi, database)

    @Provides
    @Singleton
    fun provideEventsDatabase(): Database = Room.databaseBuilder(
        application,
        Database::class.java, Constants.EVENTS_DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}