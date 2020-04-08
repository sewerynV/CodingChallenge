package com.seweryn.dazncodechallenge.data.model.event

import org.threeten.bp.LocalDateTime

data class Event(
    val id: String,
    val title: String,
    val subtitle: String,
    val date: LocalDateTime,
    val imageUrl: String,
    val videoUrl: String)