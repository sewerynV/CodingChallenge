package com.seweryn.codechallenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.seweryn.codechallenge.data.model.schedule.ScheduleResponse
import io.reactivex.Single

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM scheduleresponse")
    fun querySchedule(): Single<List<ScheduleResponse>>

    @Insert
    fun insertSchedule(events: List<ScheduleResponse>)

    @Query("DELETE FROM scheduleresponse")
    fun deleteSchedule()

}