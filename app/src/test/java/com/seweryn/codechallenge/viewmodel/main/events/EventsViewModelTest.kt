package com.seweryn.codechallenge.viewmodel.main.events

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.seweryn.codechallenge.data.EventsRepository
import com.seweryn.codechallenge.data.model.event.Event
import com.seweryn.codechallenge.utils.DateFormatter
import com.seweryn.codechallenge.utils.SchedulersProvider
import com.seweryn.codechallenge.utils.WithMockito
import com.seweryn.codechallenge.viewmodel.main.MainViewModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.threeten.bp.LocalDateTime
import java.lang.Exception

@RunWith(MockitoJUnitRunner.Silent::class)
class EventsViewModelTest : WithMockito {

    private lateinit var systemUnderTest: EventsViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var eventsRepository: EventsRepository

    @Mock
    lateinit var dateFormatter: DateFormatter

    @Mock
    lateinit var schedulersProvider: SchedulersProvider

    @Mock
    lateinit var actionListener: (MainViewModel.Action?) -> Unit

    @Mock
    lateinit var progressListener: (Boolean) -> Unit

    @Before
    fun setUp() {
        simulateEventsCanBeLoaded()
        mockSchedulers()
        mockDateFormatter()
        systemUnderTest = EventsViewModel(eventsRepository, dateFormatter, schedulersProvider)
        mockLiveData()
    }

    @Test
    fun `should load events on initialisation`() {
        verify(eventsRepository).getEvents()
    }

    @Test
    fun `should load events on retry`() {
        systemUnderTest.retry()
        verify(eventsRepository, times(2)).getEvents()

    }

    @Test
    fun `should update content when loaded events`() {
        systemUnderTest.retry()
        assertNotNull(systemUnderTest.content)
        assertEquals(1, systemUnderTest.content.value?.size)
    }

    @Test
    fun `should show progress when loading events`() {
        systemUnderTest.contentLoadingProgress.observeForever{
            progressListener.invoke(it)
        }
        systemUnderTest.retry()
        verify(progressListener).invoke(true)
    }

    @Test
    fun `should set open video action when requested`() {
        systemUnderTest.retry()
        systemUnderTest.content.value!![0].selectAction.invoke()
        verify(actionListener).invoke(any(MainViewModel.Action.PlayVideo::class.java))

    }

    @Test
    fun `should show error on fail`() {
        simulateEventsCanNotBeLoaded()
        systemUnderTest.retry()
        assertThat(systemUnderTest.contentError.value, instanceOf(MainViewModel.Error::class.java))
    }

    private fun mockLiveData() {
        systemUnderTest.content.observeForever { }
        systemUnderTest.contentError.observeForever {  }
        systemUnderTest.action.observeForever {
            actionListener.invoke(it)
        }
    }

    private fun mockDateFormatter() {
        `when`(dateFormatter.formatDateForDisplay(any())).thenReturn("date")
    }

    private fun mockSchedulers() {
        `when`(schedulersProvider.ioScheduler()).thenReturn(Schedulers.trampoline())
        `when`(schedulersProvider.uiScheduler()).thenReturn(Schedulers.trampoline())
    }

    private fun simulateEventsCanNotBeLoaded() {
        `when`(eventsRepository.getEvents()).thenReturn(Observable.error(Exception()))
    }

    private fun simulateEventsCanBeLoaded() {
        `when`(eventsRepository.getEvents()).thenReturn(Observable.just(createEvents()))
    }

    private fun createEvents() = listOf(
        Event(
            id = "id",
            title = "title",
            subtitle = "subtitle",
            date = LocalDateTime.now(),
            imageUrl = "img",
            videoUrl = "video"
        )
    )
}