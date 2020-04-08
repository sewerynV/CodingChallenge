package com.seweryn.dazncodechallenge.data.remote

import com.seweryn.dazncodechallenge.data.model.event.EventResponse
import com.seweryn.dazncodechallenge.data.model.schedule.ScheduleResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface DaznApi {
    @GET("getEvents")
    fun getEvents(): Observable<List<EventResponse>>

    @GET("getSchedule")
    fun getSchedule(): Observable<List<ScheduleResponse>>
}