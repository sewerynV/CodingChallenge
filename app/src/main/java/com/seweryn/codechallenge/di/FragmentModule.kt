package com.seweryn.codechallenge.di

import com.seweryn.codechallenge.ui.main.fragments.events.EventsFragment
import com.seweryn.codechallenge.ui.main.fragments.schedule.ScheduleFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeEventsFragment(): EventsFragment

    @ContributesAndroidInjector
    abstract fun contributeScheduleFragment(): ScheduleFragment
}