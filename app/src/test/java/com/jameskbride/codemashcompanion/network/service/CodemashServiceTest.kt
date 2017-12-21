package com.jameskbride.codemashcompanion.network.service

import com.jameskbride.codemashcompanion.bus.*
import com.jameskbride.codemashcompanion.network.CodemashApi
import com.jameskbride.codemashcompanion.network.model.ApiSession
import com.jameskbride.codemashcompanion.utils.test.buildDefaultApiSpeakers
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

    private var speakersReceivedEvent: SpeakersReceivedEvent = SpeakersReceivedEvent()
    private var sessionsReceivedEvent: SessionsReceivedEvent = SessionsReceivedEvent()
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
    fun onRequestConferenceDataEventSendsTheSpeakersReceivedEvent() {
        val speaker = buildDefaultApiSpeakers()[0]

        whenever(codemashApi.getSpeakers()).thenReturn(Observable.fromArray(listOf(speaker)))

        eventBus.post(RequestConferenceDataEvent())

        testScheduler.triggerActions()

        val actualSpeakers = speakersReceivedEvent.speakers
        assertEquals(speaker, actualSpeakers[0])
    }

    @Test
    fun onRequestConferenceDataEventSendsConferenceDataRequestErrorWhenAnErrorOccurs() {
        whenever(codemashApi.getSpeakers()).thenReturn(Observable.error(Exception("Woops!")))

        eventBus.post(RequestConferenceDataEvent())
        testScheduler.triggerActions()

        assertTrue(conferenceDataRequestErrorFired)
    }

    @Test
    fun onSpeakersPersistedEventRequestsTheSessionsData() {
        val session = ApiSession(
                id  = "123",
                category = "DevOps",
                sessionStartTime = "start time",
                sessionEndTime = "end time",
                sessionType = "session type",
                sessionTime = "session time",
                title = "title",
                abstract = "abstract"
        )

        whenever(codemashApi.getSessions()).thenReturn(Observable.fromArray(listOf(session)))

        eventBus.post(SpeakersPersistedEvent())

        testScheduler.triggerActions()

        val actualSessions = sessionsReceivedEvent.sessions
        assertEquals(session, actualSessions[0])
    }

    @Test
    fun onSpeakersPersistedEventSendsConferenceDataRequestErrorEventWhenAnErrorOccurs() {
        whenever(codemashApi.getSessions()).thenReturn(Observable.error(Exception("Woops!")))

        eventBus.post(SpeakersPersistedEvent())
        testScheduler.triggerActions()

        assertTrue(conferenceDataRequestErrorFired)
    }

    @Subscribe
    fun onSpeakersReceivedEvent(speakersReceivedEvent: SpeakersReceivedEvent) {
        this.speakersReceivedEvent = speakersReceivedEvent
    }

    @Subscribe
    fun onSessionsReceivedEvent(sessionsReceivedEvent: SessionsReceivedEvent) {
        this.sessionsReceivedEvent = sessionsReceivedEvent
    }

    @Subscribe
    fun onRequestConferenceDataErrorEvent(conferenceDataRequestError: ConferenceDataRequestError) {
        this.conferenceDataRequestErrorFired = true
    }
}