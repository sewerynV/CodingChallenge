package com.seweryn.dazncodechallenge.viewmodel.main.events

import com.seweryn.dazncodechallenge.data.DaznRepository
import com.seweryn.dazncodechallenge.data.model.event.Event
import com.seweryn.dazncodechallenge.utils.DateFormatter
import com.seweryn.dazncodechallenge.viewmodel.main.MainViewModel

class EventsViewModel(
    private val daznRepository: DaznRepository,
    dateFormatter: DateFormatter
) : MainViewModel(dateFormatter) {

    override fun start() {
        eventsLoadingProgress.postValue(true)
        load(
            command = daznRepository.getEvents(),
            onNext = { response -> content.value = createContent(response) },
            onError = { error -> parseError(error) },
            onComplete = { eventsLoadingProgress.value = false }
        )
    }


    private fun createContent(events: List<Event>): List<Content> {
        return events.map { event ->
            Content(
                title = event.title,
                subtitle = event.subtitle,
                date = formatDate(event.date),
                imageUrl = event.imageUrl,
                selectAction = { sendAction(Action.PlayVideo(event.videoUrl)) }
            )
        }
    }
}