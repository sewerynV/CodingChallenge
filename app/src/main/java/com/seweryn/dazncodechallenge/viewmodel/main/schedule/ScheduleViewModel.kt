package com.seweryn.dazncodechallenge.viewmodel.main.schedule

import com.seweryn.dazncodechallenge.data.DaznRepository
import com.seweryn.dazncodechallenge.data.model.schedule.ScheduleItem
import com.seweryn.dazncodechallenge.utils.DateFormatter
import com.seweryn.dazncodechallenge.utils.SchedulersProvider
import com.seweryn.dazncodechallenge.viewmodel.main.MainViewModel

class ScheduleViewModel(
    private val daznRepository: DaznRepository,
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
        if(content.value.isNullOrEmpty()) contentLoadingProgress.postValue(true)
        poll(
            command = daznRepository.getSchedule(),
            intervalInSeconds = 30,
            onNext = { response ->
                contentLoadingProgress.value = false
                content.value = createContent(response)
            },
            onError = { error ->
                contentLoadingProgress.value = false
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
}