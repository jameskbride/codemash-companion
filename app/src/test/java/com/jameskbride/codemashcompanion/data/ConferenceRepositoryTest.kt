package com.jameskbride.codemashcompanion.data

import com.jameskbride.codemashcompanion.bus.SpeakersPersistedEvent
import com.jameskbride.codemashcompanion.bus.SpeakersReceivedEvent
import com.jameskbride.codemashcompanion.network.Speaker
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ConferenceRepositoryTest {

    private lateinit var conferenceDao: ConferenceDao
    private lateinit var eventBus: EventBus
    private lateinit var subject: ConferenceRepository

    private lateinit var speakersPersistedEvent: SpeakersPersistedEvent

    @Before
    fun setUp() {
        conferenceDao = mock(ConferenceDao::class.java)
        eventBus = EventBus.getDefault()
        eventBus.register(this)
        subject = ConferenceRepository(conferenceDao, eventBus)
        subject.open()
    }

    @After
    fun tearDown() {
        eventBus.unregister(this)
        subject.close()
    }

    @Test
    fun onSpeakersReceivedEventItInsertsAllSpeakers() {
        val speakers = arrayOf(Speaker(
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
        ))

        eventBus.post(SpeakersReceivedEvent(speakers))

        verify(conferenceDao).insertAll(speakers)
    }

    @Test
    fun onSpeakersReceivedEventItNotifiesSpeakersPersisted() {
        val speakers = arrayOf(Speaker(
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
        ))

        eventBus.post(SpeakersReceivedEvent(speakers))

        assertNotNull(speakersPersistedEvent)
    }

    @Subscribe
    fun onSpeakersPersistedEvent(speakersPersistedEvent: SpeakersPersistedEvent) {
        this.speakersPersistedEvent = speakersPersistedEvent
    }
}