package com.jameskbride.codemashcompanion.data

import com.jameskbride.codemashcompanion.bus.SpeakersPersistedEvent
import com.jameskbride.codemashcompanion.bus.SpeakersReceivedEvent
import com.jameskbride.codemashcompanion.network.Speaker
import org.greenrobot.eventbus.EventBus
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ConferenceRepositoryTest {

    private lateinit var conferenceDao: ConferenceDao
    private lateinit var eventBus: EventBus
    private lateinit var subject: ConferenceRepository

    @Before
    fun setUp() {
        conferenceDao = mock(ConferenceDao::class.java)
        eventBus = mock(EventBus::class.java)
        subject = ConferenceRepository(conferenceDao, eventBus)
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

        subject.onSpeakersReceivedEvent(SpeakersReceivedEvent(speakers))

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


        subject.onSpeakersReceivedEvent(SpeakersReceivedEvent(speakers))

        val speakersPersistedEventCaptor = ArgumentCaptor.forClass(SpeakersPersistedEvent::class.java)

        verify(eventBus).post(speakersPersistedEventCaptor.capture())
    }
}