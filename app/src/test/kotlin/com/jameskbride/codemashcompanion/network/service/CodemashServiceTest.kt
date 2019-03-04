package com.jameskbride.codemashcompanion.network.service

import com.jameskbride.codemashcompanion.bus.*
import com.jameskbride.codemashcompanion.network.CodemashApi
import com.jameskbride.codemashcompanion.network.model.*
import com.jameskbride.codemashcompanion.utils.test.buildDefaultApiSpeakers
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class CodemashServiceTest {

    @Mock private lateinit var codemashApi: CodemashApi
    private lateinit var eventBus: EventBus
    private lateinit var testScheduler: TestScheduler

    private lateinit var subject: CodemashService

    private var speakersUpdatedEvent: SpeakersUpdatedEvent = SpeakersUpdatedEvent()
    private var sessionsUpdatedEvent: SessionsUpdatedEvent = SessionsUpdatedEvent()
    private var roomsUpdatedEvent: RoomsUpdatedEvent = RoomsUpdatedEvent()
    private var tagsUpdatedEvent: TagsUpdatedEvent = TagsUpdatedEvent()
    private var sessionSpeakersUpdatedEvent: SessionSpeakersUpdatedEvent = SessionSpeakersUpdatedEvent()
    private var conferenceDataRequestError: ConferenceDataRequestError = ConferenceDataRequestError(Throwable())
    private var conferenceDataRequestErrorFired: Boolean = false

    @Before
    fun setUp() {
        initMocks(this)

        testScheduler = TestScheduler()
        eventBus = EventBus.getDefault()
        eventBus.register(this)

        subject = CodemashService(codemashApi, eventBus, testScheduler, testScheduler)

       subject.open()
    }

    @After
    fun tearDown() {
        eventBus.unregister(this)
        subject.close()
    }

    @Test
    fun onRequestConferenceDataEventSendsTheSpeakersUpdatedEvent() {
        val speaker = buildDefaultApiSpeakers()[0]

        whenever(codemashApi.getSpeakers()).thenReturn(Observable.fromArray(listOf(speaker)))

        eventBus.post(RequestConferenceDataEvent())

        testScheduler.triggerActions()

        assertEquals(1, speakersUpdatedEvent.speakers.size)
    }

    @Test
    fun onRequestConferenceDataEventSendsConferenceDataRequestErrorWhenAnErrorOccurs() {
        whenever(codemashApi.getSpeakers()).thenReturn(Observable.error(Exception("Woops!")))

        eventBus.post(RequestConferenceDataEvent())
        testScheduler.triggerActions()

        assertTrue(conferenceDataRequestErrorFired)
    }

    @Test
    fun onSpeakersPersistedEventGetsTheSessionData() {
        val apiGrid = ApiGrid(rooms = listOf())
        whenever(codemashApi.getSessions()).thenReturn(Observable.fromArray(listOf(apiGrid)))

        eventBus.post(SpeakersPersistedEvent())

        verify(codemashApi).getSessions()
    }

    @Test
    fun onSpeakersPersistedEventUpdatesTheSessionsData() {
        val apiSession = ApiSession(
                id  = "123",
                sessionStartTime = "start time",
                sessionEndTime = "end time",
                title = "title",
                abstract = "abstract"
        )

        val apiRoom = ApiRoom(sessions = listOf(apiSession))
        val apiGrid = ApiGrid(rooms = listOf(apiRoom))

        whenever(codemashApi.getSessions()).thenReturn(Observable.fromArray(listOf(apiGrid)))

        eventBus.post(SpeakersPersistedEvent())

        testScheduler.triggerActions()

        assertTrue(sessionsUpdatedEvent.sessions.isNotEmpty())
    }

    @Test
    fun onSpeakersPersistedEventUpdatesTheRoomData() {
        val apiSession = ApiSession(
                id  = "123",
                roomId = 456,
                room = "room 1"
        )

        val apiRoom = ApiRoom(sessions = listOf(apiSession))
        val apiGrid = ApiGrid(rooms = listOf(apiRoom))

        whenever(codemashApi.getSessions()).thenReturn(Observable.fromArray(listOf(apiGrid)))

        eventBus.post(SpeakersPersistedEvent())

        testScheduler.triggerActions()

        assertEquals(1, roomsUpdatedEvent.conferenceRooms.size)
        val actualRooms = roomsUpdatedEvent.conferenceRooms
        assertEquals("${apiSession.id}", actualRooms[0].sessionId)
        assertEquals("room 1", actualRooms[0].name)
    }

    @Test
    fun onSpeakersPersistedEventUpdatesTheTagData() {
        val tagsCategory = Category(
                id = 1234,
                name = "Tags",
                categoryItems = listOf(CategoryItem(1234, name = "DevOps"), CategoryItem(id = 4567, name = ".NET")))
        val apiSession = ApiSession(
                id = "1234",
                categories = listOf(tagsCategory)
        )

        val apiRoom = ApiRoom(sessions = listOf(apiSession))
        val apiGrid = ApiGrid(rooms = listOf(apiRoom))

        whenever(codemashApi.getSessions()).thenReturn(Observable.fromArray(listOf(apiGrid)))

        eventBus.post(SpeakersPersistedEvent())

        testScheduler.triggerActions()

        assertEquals(2, tagsUpdatedEvent.tags.size)
    }

    @Test
    fun onSpeakersPersistedEventUpdatesTheSessionSpeakerData() {
        val sessionSpeaker = ShortSpeaker(id = "1234")
        val apiSession = ApiSession(
                id  = "123",
                shortSpeakers = listOf(sessionSpeaker)
        )

        val apiRoom = ApiRoom(sessions = listOf(apiSession))
        val apiGrid = ApiGrid(rooms = listOf(apiRoom))

        whenever(codemashApi.getSessions()).thenReturn(Observable.fromArray(listOf(apiGrid)))

        eventBus.post(SpeakersPersistedEvent())

        testScheduler.triggerActions()

        val actualSessionSpeaker = sessionSpeakersUpdatedEvent.sessionSpeakers[0]
        assertEquals(apiSession.id, actualSessionSpeaker.sessionId)
        assertEquals(sessionSpeaker.id, actualSessionSpeaker.speakerId)
    }

    @Test
    fun onSpeakersPersistedEventSendsConferenceDataRequestErrorEventWhenAnErrorOccurs() {
        whenever(codemashApi.getSessions()).thenReturn(Observable.error(Exception("Woops!")))

        eventBus.post(SpeakersPersistedEvent())
        testScheduler.triggerActions()

        assertTrue(conferenceDataRequestErrorFired)
    }

    @Subscribe
    fun onSpeakersReceivedEvent(speakersReceivedEvent: SpeakersUpdatedEvent) {
        this.speakersUpdatedEvent = speakersReceivedEvent
    }

    @Subscribe
    fun onSessionsUpdatedEvent(sessionsUpdatedEvent: SessionsUpdatedEvent) {
        this.sessionsUpdatedEvent = sessionsUpdatedEvent
    }

    @Subscribe
    fun onRoomsUpdatedEvent(roomsUpdatedEvent: RoomsUpdatedEvent) {
        this.roomsUpdatedEvent = roomsUpdatedEvent
    }

    @Subscribe
    fun onTagsUpdatedEvent(tagsUpdatedEvent: TagsUpdatedEvent) {
        this.tagsUpdatedEvent = tagsUpdatedEvent
    }

    @Subscribe
    fun onSessionSpeakersUpdatedEvent(sessionSpeakersUpdatedEvent: SessionSpeakersUpdatedEvent) {
        this.sessionSpeakersUpdatedEvent = sessionSpeakersUpdatedEvent
    }

    @Subscribe
    fun onRequestConferenceDataErrorEvent(conferenceDataRequestError: ConferenceDataRequestError) {
        conferenceDataRequestError.error.printStackTrace()
        this.conferenceDataRequestErrorFired = true
        this.conferenceDataRequestError = conferenceDataRequestError
    }
}