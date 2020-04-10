package com.seweryn.dazncodechallenge.di

import com.seweryn.dazncodechallenge.ui.main.fragments.events.EventsFragment
import com.seweryn.dazncodechallenge.ui.main.fragments.schedule.ScheduleFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeEventsFragment(): EventsFragment

    @ContributesAndroidInjector
    abstract fun contributeScheduleFragment(): ScheduleFragment
}