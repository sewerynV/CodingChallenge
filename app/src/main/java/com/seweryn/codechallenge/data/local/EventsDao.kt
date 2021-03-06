package com.seweryn.codechallenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.seweryn.codechallenge.data.model.event.EventResponse
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