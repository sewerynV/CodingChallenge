package com.seweryn.codechallenge.data.model.schedule

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScheduleResponse(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "subtitle") val subtitle: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String)