package com.seweryn.codechallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seweryn.codechallenge.data.model.event.EventResponse
import com.seweryn.codechallenge.data.model.schedule.ScheduleResponse

@Database(entities = [EventResponse::class, ScheduleResponse::class], version = 2)
abstract class Database  : RoomDatabase() {
    abstract fun eventsDao(): EventsDao

    abstract fun scheduleDao(): ScheduleDao
}