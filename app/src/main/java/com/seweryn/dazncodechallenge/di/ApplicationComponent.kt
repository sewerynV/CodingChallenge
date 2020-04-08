package com.seweryn.dazncodechallenge.di

import com.seweryn.dazncodechallenge.App
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, ApplicationModule::class, NetworkModule::class, FragmentModule::class, ViewModelModule::class])
interface ApplicationComponent : AndroidInjector<App>