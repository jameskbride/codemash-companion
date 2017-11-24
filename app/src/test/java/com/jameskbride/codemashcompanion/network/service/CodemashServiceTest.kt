package com.jameskbride.codemashcompanion.network.service

import com.jameskbride.codemashcompanion.bus.RequestConferenceDataEvent
import com.jameskbride.codemashcompanion.bus.SpeakersReceivedEvent
import com.jameskbride.codemashcompanion.network.CodemashApi
import com.jameskbride.codemashcompanion.network.Speaker
import com.jameskbride.codemashcompanion.network.SpeakerResponse
import io.reactivex.Single
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

    @Before
    fun setUp() {
        codemashApi = mock(CodemashApi::class.java)
        testScheduler = TestScheduler()
        eventBus = EventBus.getDefault()
        eventBus.register(this)

        subject = CodemashService(codemashApi, eventBus, testScheduler, testScheduler)

        eventBus.register(subject)
    }

    @After
    fun tearDown() {
        eventBus.unregister(this)
        eventBus.unregister(subject)
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

        val speakerResponse = SpeakerResponse(speakers = arrayOf(speaker))
        `when`(codemashApi.getSpeakers()).thenReturn(Single.just(speakerResponse))

        eventBus.post(RequestConferenceDataEvent())

        testScheduler.triggerActions()

        val actualSpeakers = speakersReceivedEvent.speakers
        assertEquals(speaker, actualSpeakers[0])
    }

    @Subscribe
    fun onSpeakersReceivedEvent(speakersReceivedEvent: SpeakersReceivedEvent) {
        this.speakersReceivedEvent = speakersReceivedEvent
    }
}