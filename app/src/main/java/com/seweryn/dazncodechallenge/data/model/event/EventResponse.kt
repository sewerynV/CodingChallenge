package com.seweryn.dazncodechallenge.data.model.event

data class EventResponse(
    val id: String,
    val title: String,
    val subtitle: String,
    val date: String,
    val imageUrl: String,
    val videoUrl: String)