package com.seweryn.dazncodechallenge.data

import com.seweryn.dazncodechallenge.data.model.event.Event
import com.seweryn.dazncodechallenge.data.model.schedule.ScheduleItem
import io.reactivex.Observable

interface DaznRepository {
    fun getEvents(): Observable<List<Event>>

    fun getSchedule(): Observable<List<ScheduleItem>>
}