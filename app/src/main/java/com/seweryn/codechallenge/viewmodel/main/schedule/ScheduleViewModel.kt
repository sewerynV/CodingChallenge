package com.seweryn.codechallenge.viewmodel.main.schedule

import com.seweryn.codechallenge.data.EventsRepository
import com.seweryn.codechallenge.data.model.schedule.ScheduleItem
import com.seweryn.codechallenge.utils.DateFormatter
import com.seweryn.codechallenge.utils.SchedulersProvider
import com.seweryn.codechallenge.viewmodel.main.MainViewModel

class ScheduleViewModel(
    private val eventsRepository: EventsRepository,
    dateFormatter: DateFormatter,
    schedulersProvider: SchedulersProvider
) : MainViewModel(dateFormatter, schedulersProvider) {

    override fun retry() {
        clearSubscriptions()
        loadSchedule()
    }

    fun start() {
        loadSchedule()
    }

    fun stop() {
        clearSubscriptions()
    }

    private fun loadSchedule() {
        toggleProgress(true)
        contentError.value = null
        poll(
            command = eventsRepository.getSchedule(),
            intervalInSeconds = 30,
            onNext = { response ->
                toggleProgress(false)
                content.value = createContent(response)
            },
            onError = { error ->
                toggleProgress(false)
                parseError(error)
            },
            onComplete = { }
        )
    }

    private fun createContent(events: List<ScheduleItem>): List<ContentItem> {
        return events.map { event ->
            ContentItem(
                title = event.title,
                subtitle = event.subtitle,
                date = formatDate(event.date),
                imageUrl = event.imageUrl
            )
        }
    }

    private fun toggleProgress(isInProgress: Boolean) {
        if(content.value.isNullOrEmpty()) contentLoadingProgress.postValue(isInProgress)
    }
}