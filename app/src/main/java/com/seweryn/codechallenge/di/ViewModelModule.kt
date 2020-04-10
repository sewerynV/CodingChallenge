package com.seweryn.codechallenge.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seweryn.codechallenge.data.EventsRepository
import com.seweryn.codechallenge.utils.DateFormatter
import com.seweryn.codechallenge.utils.SchedulersProvider
import com.seweryn.codechallenge.viewmodel.main.events.EventsViewModel
import com.seweryn.codechallenge.viewmodel.main.schedule.ScheduleViewModel
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
    fun provideEventsViewModel(eventsRepository: EventsRepository, dateFormatter: DateFormatter, schedulersProvider: SchedulersProvider): ViewModel {
        return EventsViewModel(
            eventsRepository,
            dateFormatter,
            schedulersProvider
        )
    }

    @Provides
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    fun provideScheduleViewModel(eventsRepository: EventsRepository, dateFormatter: DateFormatter, schedulersProvider: SchedulersProvider): ViewModel {
        return ScheduleViewModel(
            eventsRepository,
            dateFormatter,
            schedulersProvider
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