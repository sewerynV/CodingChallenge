package com.seweryn.dazncodechallenge.data.model.schedule

import org.threeten.bp.LocalDateTime

data class Schedule(
    val id: String,
    val title: String,
    val subtitle: String,
    val date: LocalDateTime,
    val imageUrl: String)