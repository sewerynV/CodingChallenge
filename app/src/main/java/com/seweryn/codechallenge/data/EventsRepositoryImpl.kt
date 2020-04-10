package com.seweryn.codechallenge.data

import com.seweryn.codechallenge.data.local.Database
import com.seweryn.codechallenge.data.model.event.Event
import com.seweryn.codechallenge.data.model.schedule.ScheduleItem
import com.seweryn.codechallenge.data.remote.EventsApi
import io.reactivex.Observable
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class EventsRepositoryImpl(private val apiInterface: EventsApi, private val database: Database) :
    EventsRepository {

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