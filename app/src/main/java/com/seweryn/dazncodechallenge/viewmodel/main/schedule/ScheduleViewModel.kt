package com.seweryn.dazncodechallenge.viewmodel.main.schedule

import com.seweryn.dazncodechallenge.data.DaznRepository
import com.seweryn.dazncodechallenge.data.model.schedule.Schedule
import com.seweryn.dazncodechallenge.utils.DateFormatter
import com.seweryn.dazncodechallenge.viewmodel.main.MainViewModel

class ScheduleViewModel(
    private val daznRepository: DaznRepository,
    dateFormatter: DateFormatter
) : MainViewModel(dateFormatter) {

    override fun start() {
        if (content.value.isNullOrEmpty()) eventsLoadingProgress.postValue(true)
        poll(
            command = daznRepository.getSchedule(),
            intervalInSeconds = 30,
            onNext = { response -> content.value = createContent(response) },
            onError = { error -> parseError(error) },
            onComplete = { eventsLoadingProgress.value = false }
        )
    }

    private fun createContent(events: List<Schedule>): List<Content> {
        return events.map { event ->
            Content(
                title = event.title,
                subtitle = event.subtitle,
                date = formatDate(event.date),
                imageUrl = event.imageUrl
            )
        }
    }
}