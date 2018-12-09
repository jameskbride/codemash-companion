package com.jameskbride.codemashcompanion.network.service

import com.jameskbride.codemashcompanion.bus.*
import com.jameskbride.codemashcompanion.network.CodemashApi
import com.jameskbride.codemashcompanion.network.model.ApiSession
import com.jameskbride.codemashcompanion.network.model.ShortSpeaker
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
        whenever(codemashApi.getSessions()).thenReturn(Observable.fromArray(listOf()))

        eventBus.post(SpeakersPersistedEvent())

        verify(codemashApi).getSessions()
    }

    @Test
    fun onSpeakersPersistedEventUpdatesTheSessionsData() {
        val apiSession = ApiSession(
                id  = 123,
                category = "DevOps",
                sessionStartTime = "start time",
                sessionEndTime = "end time",
                sessionType = "session type",
                sessionTime = "session time",
                title = "title",
                abstract = "abstract"
        )

        whenever(codemashApi.getSessions()).thenReturn(Observable.fromArray(listOf(apiSession)))

        eventBus.post(SpeakersPersistedEvent())

        testScheduler.triggerActions()

        val actualSession = sessionsUpdatedEvent.sessions[0]
        assertEquals(apiSession.id.toString(), actualSession.Id)
        assertEquals(apiSession.category, actualSession.Category)
        assertEquals(apiSession.sessionStartTime, actualSession.SessionStartTime)
        assertEquals(apiSession.sessionEndTime, actualSession.SessionEndTime)
        assertEquals(apiSession.sessionType, actualSession.SessionType)
        assertEquals(apiSession.sessionTime, actualSession.SessionTime)
        assertEquals(apiSession.title, actualSession.Title)
        assertEquals(apiSession.abstract, actualSession.Abstract)
    }

    @Test
    fun onSpeakersPersistedEventUpdatesTheRoomData() {
        val apiSession = ApiSession(
                id  = 123,
                rooms = listOf("room 1", "room 2")
        )

        whenever(codemashApi.getSessions()).thenReturn(Observable.fromArray(listOf(apiSession)))

        eventBus.post(SpeakersPersistedEvent())

        testScheduler.triggerActions()

        assertEquals(2, roomsUpdatedEvent.conferenceRooms.size)
        val actualRooms = roomsUpdatedEvent.conferenceRooms
        assertEquals("${apiSession.id}", actualRooms[0].sessionId)
        assertEquals("room 1", actualRooms[0].name)
        assertEquals("${apiSession.id}", actualRooms[1].sessionId)
        assertEquals("room 2", actualRooms[1].name)
    }

    @Test
    fun onSpeakersPersistedEventUpdatesTheTagData() {
        val apiSession = ApiSession(
                id  = 123,
                tags = listOf("tag 1", "tag 2")
        )

        whenever(codemashApi.getSessions()).thenReturn(Observable.fromArray(listOf(apiSession)))

        eventBus.post(SpeakersPersistedEvent())

        testScheduler.triggerActions()

        assertEquals(2, tagsUpdatedEvent.tags.size)
        val actualTags = tagsUpdatedEvent.tags
        assertEquals("${apiSession.id}", actualTags[0].sessionId)
        assertEquals("tag 1", actualTags[0].name)
        assertEquals("${apiSession.id}", actualTags[1].sessionId)
        assertEquals("tag 2", actualTags[1].name)
    }

    @Test
    fun onSpeakersPersistedEventUpdatesTheSessionSpeakerData() {
        val sessionSpeaker = ShortSpeaker(id = "1234", firstName = "first", lastName = "last", gravatarUrl = "url")
        val apiSession = ApiSession(
                id  = 123,
                shortSpeakers = listOf(sessionSpeaker)
        )

        whenever(codemashApi.getSessions()).thenReturn(Observable.fromArray(listOf(apiSession)))

        eventBus.post(SpeakersPersistedEvent())

        testScheduler.triggerActions()

        val actualSessionSpeaker = sessionSpeakersUpdatedEvent.sessionSpeakers[0]
        assertEquals(apiSession.id.toString(), actualSessionSpeaker.sessionId)
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
        this.conferenceDataRequestErrorFired = true
    }
}