package com.seweryn.codechallenge.viewmodel.main.events

import com.seweryn.codechallenge.data.EventsRepository
import com.seweryn.codechallenge.data.model.event.Event
import com.seweryn.codechallenge.utils.DateFormatter
import com.seweryn.codechallenge.utils.SchedulersProvider
import com.seweryn.codechallenge.viewmodel.main.MainViewModel

class EventsViewModel(
    private val eventsRepository: EventsRepository,
    dateFormatter: DateFormatter,
    schedulersProvider: SchedulersProvider
) : MainViewModel(dateFormatter, schedulersProvider) {

    init {
        loadEvents()
    }

    override fun retry() {
        clearSubscriptions()
        loadEvents()
    }

    private fun loadEvents() {
        contentLoadingProgress.postValue(true)
        load(
            command = eventsRepository.getEvents(),
            onNext = { response ->
                content.value = createContent(response)
            },
            onError = { error ->
                contentLoadingProgress.value = false
                parseError(error)
            },
            onComplete = { contentLoadingProgress.value = false }
        )
    }

    private fun createContent(events: List<Event>): List<ContentItem> {
        return events.map { event ->
            ContentItem(
                title = event.title,
                subtitle = event.subtitle,
                date = formatDate(event.date),
                imageUrl = event.imageUrl,
                selectAction = { sendAction(Action.PlayVideo(event.videoUrl)) }
            )
        }
    }
}