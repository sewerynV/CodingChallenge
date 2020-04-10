package com.seweryn.dazncodechallenge.data

import com.seweryn.dazncodechallenge.data.local.Database
import com.seweryn.dazncodechallenge.data.local.EventsDao
import com.seweryn.dazncodechallenge.data.local.ScheduleDao
import com.seweryn.dazncodechallenge.data.model.event.EventResponse
import com.seweryn.dazncodechallenge.data.model.schedule.ScheduleResponse
import com.seweryn.dazncodechallenge.data.remote.DaznApi
import com.seweryn.dazncodechallenge.utils.WithMockito
import io.reactivex.Observable
import io.reactivex.Single
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.inOrder
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class DaznRepositoryImplTest : WithMockito {

    private lateinit var systemUnderTest: DaznRepositoryImpl

    @Mock
    lateinit var apiInterface: DaznApi

    @Mock
    lateinit var database: Database

    @Mock
    lateinit var eventsDao: EventsDao

    @Mock
    lateinit var scheduleDao: ScheduleDao

    @Before
    fun setUp() {
        systemUnderTest = DaznRepositoryImpl(apiInterface, database)
        mockDatabase()
    }

    @Test
    fun `should load data from database before api when fetching events`() {
        simulateThatEventsCanBeFetchedFromApi()
        simulateThatEventsCanBeFetchedFromDatabase()
        systemUnderTest.getEvents()

        val inOrder = inOrder(eventsDao, apiInterface)

        inOrder.verify(eventsDao).queryEvents()
        inOrder.verify(apiInterface).getEvents()
    }

    @Test
    fun `should deliver data from database before api when fetching events`() {
        simulateThatEventsCanBeFetchedFromApi()
        simulateThatEventsCanBeFetchedFromDatabase()
        systemUnderTest.getEvents().test().values().forEachIndexed { index, list ->
            assertThat(
                list[0].id,
                CoreMatchers.containsString(if (index == 0) "db" else "api"))
        }
    }

    @Test
    fun `should clear database and add new events when they were fetched from api`() {
        simulateThatEventsCanBeFetchedFromDatabase()
        simulateThatEventsCanBeFetchedFromApi()
        systemUnderTest.getEvents().subscribe()

        val inOrder = inOrder(eventsDao)

        inOrder.verify(eventsDao).deleteAllEvents()
        inOrder.verify(eventsDao).insertEvents(any())

    }

    @Test
    fun `should sort events by date in ascending order`() {
        simulateThatEventsCanBeFetchedFromDatabase()
        simulateThatEventsCanBeFetchedFromApi()
        systemUnderTest.getEvents().test().values().last().let {
            assertEquals("2", it[0].id.takeLast(1))
            assertEquals("1", it[1].id.takeLast(1))
        }
    }

    @Test
    fun `should load data from database before api when fetching schedule`() {
        simulateThatScheduleCanBeFetchedFromApi()
        simulateThatScheduleCanBeFetchedFromDatabase()
        systemUnderTest.getSchedule()

        val inOrder = inOrder(scheduleDao, apiInterface)

        inOrder.verify(scheduleDao).querySchedule()
        inOrder.verify(apiInterface).getSchedule()
    }

    @Test
    fun `should deliver data from database before api when fetching schedule`() {
        simulateThatScheduleCanBeFetchedFromApi()
        simulateThatScheduleCanBeFetchedFromDatabase()
        systemUnderTest.getSchedule().test().values().forEachIndexed { index, list ->
            assertThat(
                list[0].id,
                CoreMatchers.containsString(if (index == 0) "db" else "api"))
        }
    }

    @Test
    fun `should clear database and add new schedule when it was fetched from api`() {
        simulateThatScheduleCanBeFetchedFromApi()
        simulateThatScheduleCanBeFetchedFromDatabase()
        systemUnderTest.getSchedule().subscribe()

        val inOrder = inOrder(scheduleDao)

        inOrder.verify(scheduleDao).deleteSchedule()
        inOrder.verify(scheduleDao).insertSchedule(any())

    }

    @Test
    fun `should sort schedule items by date in ascending order`() {
        simulateThatScheduleCanBeFetchedFromApi()
        simulateThatScheduleCanBeFetchedFromDatabase()
        systemUnderTest.getSchedule().test().values().last().let {
            assertEquals("2", it[0].id.takeLast(1))
            assertEquals("1", it[1].id.takeLast(1))
        }
    }

    private fun mockDatabase() {
        `when`(database.eventsDao()).thenReturn(eventsDao)
        `when`(database.scheduleDao()).thenReturn(scheduleDao)
    }

    private fun simulateThatEventsCanBeFetchedFromDatabase() {
        `when`(eventsDao.queryEvents()).thenReturn(Single.just(createEvents("db")))
    }

    private fun simulateThatEventsCanBeFetchedFromApi() {
        `when`(apiInterface.getEvents()).thenReturn(Observable.just(createEvents("api")))
    }

    private fun simulateThatScheduleCanBeFetchedFromDatabase() {
        `when`(scheduleDao.querySchedule()).thenReturn(Single.just(createSchedule("db")))
    }

    private fun simulateThatScheduleCanBeFetchedFromApi() {
        `when`(apiInterface.getSchedule()).thenReturn(Observable.just(createSchedule("api")))
    }

    private fun createEvents(idFormat: String): List<EventResponse> = listOf(
        createEvent("${idFormat}1", "2020-02-02T12:12:12.122Z"),
        createEvent("${idFormat}2", "2020-02-01T12:12:12.122Z")
    )

    private fun createEvent(id: String, date: String): EventResponse {
        return EventResponse(
            id = id,
            title = "title",
            subtitle = "subtitle",
            date = date,
            imageUrl = "img",
            videoUrl = "video"
        )
    }

    private fun createSchedule(idFormat: String): List<ScheduleResponse> = listOf(
        createScheduleResponse("${idFormat}1", "2020-02-02T12:12:12.122Z"),
        createScheduleResponse("${idFormat}2", "2020-02-01T12:12:12.122Z")
    )

    private fun createScheduleResponse(id: String, date: String): ScheduleResponse {
        return ScheduleResponse(
            id = id,
            title = "title",
            subtitle = "subtitle",
            date = date,
            imageUrl = "img"
        )
    }
}