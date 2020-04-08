package com.seweryn.dazncodechallenge.data

import com.seweryn.dazncodechallenge.data.model.event.Event
import com.seweryn.dazncodechallenge.data.model.schedule.Schedule
import io.reactivex.Observable

interface DaznRepository {
    fun getEvents(): Observable<List<Event>>

    fun getSchedule(): Observable<List<Schedule>>
}