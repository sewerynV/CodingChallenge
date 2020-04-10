package com.seweryn.dazncodechallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seweryn.dazncodechallenge.data.model.event.EventResponse
import com.seweryn.dazncodechallenge.data.model.schedule.ScheduleResponse

@Database(entities = [EventResponse::class, ScheduleResponse::class], version = 2)
abstract class Database  : RoomDatabase() {
    abstract fun eventsDao(): EventsDao

    abstract fun scheduleDao(): ScheduleDao
}