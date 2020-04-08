package com.seweryn.dazncodechallenge.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seweryn.dazncodechallenge.data.DaznRepository
import com.seweryn.dazncodechallenge.utils.DateFormatter
import com.seweryn.dazncodechallenge.viewmodel.main.events.EventsViewModel
import com.seweryn.dazncodechallenge.viewmodel.main.schedule.ScheduleViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import kotlin.reflect.KClass

@Module
class ViewModelModule {

    @MapKey
    @Target(AnnotationTarget.FUNCTION)
    annotation class ViewModelKey(
        val value: KClass<out ViewModel>
    )

    @Provides
    @IntoMap
    @ViewModelKey(EventsViewModel::class)
    fun provideEventsViewModel(daznRepository: DaznRepository, dateFormatter: DateFormatter): ViewModel {
        return EventsViewModel(
            daznRepository,
            dateFormatter
        )
    }

    @Provides
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    fun provideScheduleViewModel(daznRepository: DaznRepository, dateFormatter: DateFormatter): ViewModel {
        return ScheduleViewModel(
            daznRepository,
            dateFormatter
        )
    }

    @Provides
    fun provideViewModelFactory(
        providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return requireNotNull(providers[modelClass as Class<out ViewModel>]).get() as T
        }
    }

}