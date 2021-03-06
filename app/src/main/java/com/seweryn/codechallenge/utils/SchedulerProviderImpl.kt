package com.seweryn.codechallenge.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProviderImpl : SchedulersProvider {
    override fun ioScheduler(): Scheduler = Schedulers.io()

    override fun uiScheduler(): Scheduler = AndroidSchedulers.mainThread()

}