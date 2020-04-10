package com.seweryn.codechallenge.data.remote

import com.seweryn.codechallenge.data.model.event.EventResponse
import com.seweryn.codechallenge.data.model.schedule.ScheduleResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface EventsApi {
    @GET("getEvents")
    fun getEvents(): Observable<List<EventResponse>>

    @GET("getSchedule")
    fun getSchedule(): Observable<List<ScheduleResponse>>
}