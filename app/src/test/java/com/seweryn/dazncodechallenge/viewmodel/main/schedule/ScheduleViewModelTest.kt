package com.seweryn.dazncodechallenge.viewmodel.main.schedule

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.seweryn.dazncodechallenge.data.DaznRepository
import com.seweryn.dazncodechallenge.data.model.schedule.ScheduleItem
import com.seweryn.dazncodechallenge.utils.DateFormatter
import com.seweryn.dazncodechallenge.utils.SchedulersProvider
import com.seweryn.dazncodechallenge.utils.WithMockito
import com.seweryn.dazncodechallenge.viewmodel.main.MainViewModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.threeten.bp.LocalDateTime

@RunWith(MockitoJUnitRunner.Silent::class)
class ScheduleViewModelTest : WithMockito {

    private lateinit var systemUnderTest: ScheduleViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var daznRepository: DaznRepository

    @Mock
    lateinit var dateFormatter: DateFormatter

    @Mock
    lateinit var schedulersProvider: SchedulersProvider

    @Mock
    lateinit var progressListener: (Boolean) -> Unit

    @Before
    fun setUp() {
        systemUnderTest = ScheduleViewModel(daznRepository, dateFormatter, schedulersProvider)
        mockDateFormatter()
        mockLiveData()
        mockSchedulers()
    }

    @Test
    fun `should load schedule on start`() {
        simulateScheduleCanBeFetched()
        systemUnderTest.start()
        verify(daznRepository).getSchedule()
    }

    @Test
    fun `should load schedule on retry`() {
        simulateScheduleCanBeFetched()
        systemUnderTest.retry()
        verify(daznRepository).getSchedule()
    }

    @Test
    fun `should show progress when loading schedule`() {
        simulateScheduleCanBeFetched()
        systemUnderTest.contentLoadingProgress.observeForever {
            progressListener.invoke(it)
        }
        systemUnderTest.start()
        verify(progressListener).invoke(true)
    }

    @Test
    fun `should update content when fetched schedule`() {
        simulateScheduleCanBeFetched()
        systemUnderTest.start()
        assertEquals(1, systemUnderTest.content.value?.size)
    }

    @Test
    fun `should show error on fail`() {
        simulateEventsCanNotBeFetched()
        systemUnderTest.start()
        assertThat(
            systemUnderTest.contentError.value,
            CoreMatchers.instanceOf(MainViewModel.Error::class.java)
        )
    }

    private fun mockLiveData() {
        systemUnderTest.content.observeForever { }
        systemUnderTest.contentError.observeForever { }
    }

    private fun mockDateFormatter() {
        Mockito.`when`(dateFormatter.formatDateForDisplay(any())).thenReturn("date")
    }

    private fun mockSchedulers() {
        Mockito.`when`(schedulersProvider.ioScheduler()).thenReturn(Schedulers.trampoline())
        Mockito.`when`(schedulersProvider.uiScheduler()).thenReturn(Schedulers.trampoline())
    }

    private fun simulateEventsCanNotBeFetched() {
        Mockito.`when`(daznRepository.getSchedule()).thenReturn(Observable.error(Exception()))
    }

    private fun simulateScheduleCanBeFetched() {
        Mockito.`when`(daznRepository.getSchedule()).thenReturn(Observable.just(createSchedule()))
    }

    private fun createSchedule() = listOf(
        ScheduleItem(
            id = "id",
            title = "title",
            subtitle = "subtitle",
            date = LocalDateTime.now(),
            imageUrl = "img"
        )
    )
}