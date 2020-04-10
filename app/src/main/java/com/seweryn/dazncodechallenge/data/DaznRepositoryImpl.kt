package com.seweryn.dazncodechallenge.data

import com.seweryn.dazncodechallenge.data.local.Database
import com.seweryn.dazncodechallenge.data.model.event.Event
import com.seweryn.dazncodechallenge.data.model.schedule.ScheduleItem
import com.seweryn.dazncodechallenge.data.remote.DaznApi
import io.reactivex.Observable
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class DaznRepositoryImpl(private val apiInterface: DaznApi, private val database: Database) :
    DaznRepository {

    private val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    override fun getEvents(): Observable<List<Event>> {
        return Observable.concat(database.eventsDao().queryEvents().toObservable(),
            apiInterface.getEvents().doOnNext {
                database.eventsDao().deleteAllEvents()
                database.eventsDao().insertEvents(it)
            }
        ).map {
            it.map { event ->
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
            }.sortedBy { it.date }
        }
    }

    override fun getSchedule(): Observable<List<ScheduleItem>> {
        return Observable.concat(
            database.scheduleDao().querySchedule().toObservable(),
            apiInterface.getSchedule().doOnNext {
                database.scheduleDao().deleteSchedule()
                database.scheduleDao().insertSchedule(it)
            }
        ).map {
                it.map { event ->
                    ScheduleItem(
                        id = event.id,
                        title = event.title,
                        subtitle = event.subtitle,
                        date = LocalDateTime.parse(
                            event.date,
                            DateTimeFormatter.ofPattern(DATE_FORMAT)
                        ),
                        imageUrl = event.imageUrl
                    )
                }.sortedBy { it.date }
            }
    }
}