package com.jameskbride.codemashcompanion.network.service

import com.jameskbride.codemashcompanion.bus.RequestConferenceDataEvent
import com.jameskbride.codemashcompanion.bus.SessionsReceivedEvent
import com.jameskbride.codemashcompanion.bus.SpeakersPersistedEvent
import com.jameskbride.codemashcompanion.bus.SpeakersReceivedEvent
import com.jameskbride.codemashcompanion.network.CodemashApi
import com.jameskbride.codemashcompanion.network.Session
import com.jameskbride.codemashcompanion.network.Speaker
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CodemashServiceTest {

    private lateinit var codemashApi: CodemashApi
    private lateinit var eventBus: EventBus
    private lateinit var testScheduler: TestScheduler

    private lateinit var subject: CodemashService

    private var speakersReceivedEvent: SpeakersReceivedEvent = SpeakersReceivedEvent()
    private var sessionsReceivedEvent: SessionsReceivedEvent = SessionsReceivedEvent()

    @Before
    fun setUp() {
        codemashApi = mock(CodemashApi::class.java)
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
        val speaker = Speaker(
                LinkedInProfile = "linkedin",
                Id = "1234",
                LastName = "Smith",
                SessionIds = arrayOf("1", "2"),
                TwitterLink = "twitter",
                GitHubLink = "github",
                FirstName = "John",
                GravatarUrl = "gravitar",
                Biography = "biography",
                BlogUrl = "blog"
                )

        `when`(codemashApi.getSpeakers()).thenReturn(Observable.fromArray(arrayOf(speaker)))

        eventBus.post(RequestConferenceDataEvent())

        testScheduler.triggerActions()

        val actualSpeakers = speakersReceivedEvent.speakers
        assertEquals(speaker, actualSpeakers[0])
    }

    @Test
    fun onSpeakersPersistedEventRequestsTheSessionsData() {
        val session = Session(
                Id  = "123",
                Category = "DevOps",
                SessionStartTime = "start time",
                SessionEndTime = "end time",
                SessionType = "session type",
                SessionTime = "session time",
                Title = "title",
                Abstract = "abstract",
                speakers = arrayOf("john", "smith"),
                tags = arrayOf("tag1", "tag2")
        )

        `when`(codemashApi.getSessions()).thenReturn(Observable.fromArray(arrayOf(session)))

        eventBus.post(SpeakersPersistedEvent())

        testScheduler.triggerActions()

        val actualSessions = sessionsReceivedEvent.sessions
        assertEquals(session, actualSessions[0])
    }

    @Subscribe
    fun onSpeakersReceivedEvent(speakersReceivedEvent: SpeakersReceivedEvent) {
        this.speakersReceivedEvent = speakersReceivedEvent
    }

    @Subscribe
    fun onSessionsReceivedEvent(sessionsReceivedEvent: SessionsReceivedEvent) {
        this.sessionsReceivedEvent = sessionsReceivedEvent
    }
}