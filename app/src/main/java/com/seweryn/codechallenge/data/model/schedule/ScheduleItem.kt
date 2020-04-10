package com.seweryn.codechallenge.data.model.schedule

import org.threeten.bp.LocalDateTime

data class ScheduleItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val date: LocalDateTime,
    val imageUrl: String)