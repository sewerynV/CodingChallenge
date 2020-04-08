package com.seweryn.dazncodechallenge.data

import com.seweryn.dazncodechallenge.data.model.event.Event
import com.seweryn.dazncodechallenge.data.model.schedule.Schedule
import com.seweryn.dazncodechallenge.data.remote.DaznApi
import io.reactivex.Observable
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class DaznRepositoryImpl(private val apiInterface: DaznApi): DaznRepository {

    private val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    override fun getEvents(): Observable<List<Event>> {
        return apiInterface.getEvents()
            .map { it.map { event->
                Event(
                    id = event.id,
                    title = event.title,
                    subtitle = event.subtitle,
                    date = LocalDateTime.parse(
                        event.date,
                        DateTimeFormatter.ofPattern(DATE_FORMAT)
                    ),
                    imageUrl = event.imageUrl,
                    videoUrl = event.videoUrl
                )
            }.sortedBy { it.date } }
    }

    override fun getSchedule(): Observable<List<Schedule>> {
        return apiInterface.getSchedule()
            .map { it.map { event->
                Schedule(
                    id = event.id,
                    title = event.title,
                    subtitle = event.subtitle,
                    date = LocalDateTime.parse(
                        event.date,
                        DateTimeFormatter.ofPattern(DATE_FORMAT)
                    ),
                    imageUrl = event.imageUrl
                )
            }.sortedBy { it.date } }
    }
}