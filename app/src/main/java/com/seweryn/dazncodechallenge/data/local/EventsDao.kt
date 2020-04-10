package com.seweryn.dazncodechallenge.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.seweryn.dazncodechallenge.data.model.event.Event
import com.seweryn.dazncodechallenge.data.model.event.EventResponse
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface EventsDao {
    @Query("SELECT * FROM eventresponse")
    fun queryEvents(): Single<List<EventResponse>>

    @Insert
    fun insertEvents(events: List<EventResponse>)

    @Query("DELETE FROM eventresponse")
    fun deleteAllEvents()

}