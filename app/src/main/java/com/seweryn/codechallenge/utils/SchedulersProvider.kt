package com.seweryn.codechallenge.utils

import io.reactivex.Scheduler

interface SchedulersProvider {
    fun ioScheduler(): Scheduler

    fun uiScheduler(): Scheduler
}