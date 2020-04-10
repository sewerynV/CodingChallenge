package com.seweryn.codechallenge.data

import com.seweryn.codechallenge.data.model.event.Event
import com.seweryn.codechallenge.data.model.schedule.ScheduleItem
import io.reactivex.Observable

interface EventsRepository {
    fun getEvents(): Observable<List<Event>>

    fun getSchedule(): Observable<List<ScheduleItem>>
}