package com.seweryn.dazncodechallenge.viewmodel.main.events

import com.seweryn.dazncodechallenge.data.DaznRepository
import com.seweryn.dazncodechallenge.data.model.event.Event
import com.seweryn.dazncodechallenge.utils.DateFormatter
import com.seweryn.dazncodechallenge.utils.SchedulersProvider
import com.seweryn.dazncodechallenge.viewmodel.main.MainViewModel

class EventsViewModel(
    private val daznRepository: DaznRepository,
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
            command = daznRepository.getEvents(),
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