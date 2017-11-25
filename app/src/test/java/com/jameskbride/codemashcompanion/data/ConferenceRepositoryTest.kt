package com.jameskbride.codemashcompanion.data

import com.jameskbride.codemashcompanion.bus.ConferenceDataPersistedEvent
import com.jameskbride.codemashcompanion.bus.SessionsReceivedEvent
import com.jameskbride.codemashcompanion.bus.SpeakersPersistedEvent
import com.jameskbride.codemashcompanion.bus.SpeakersReceivedEvent
import com.jameskbride.codemashcompanion.network.Session
import com.jameskbride.codemashcompanion.network.Speaker
import io.mockk.*
import org.greenrobot.eventbus.EventBus
import org.junit.After
import org.junit.Before
import org.junit.Test

class ConferenceRepositoryTest {

    private lateinit var conferenceDao: ConferenceDao
    private lateinit var eventBus: EventBus
    private lateinit var subject: ConferenceRepository

    @Before
    fun setUp() {
        conferenceDao = mockk()
        eventBus = mockk()
        subject = ConferenceRepository(conferenceDao, eventBus)

        every { eventBus.post(any<ConferenceDataPersistedEvent>()) } just Runs
        every { eventBus.isRegistered(subject) } returns false
        every { eventBus.register(subject) } just Runs
        every { eventBus.unregister(subject) } just Runs
    }

    @After
    fun tearDown() {
        subject.close()
    }

    @Test
    fun onSpeakersReceivedEventItInsertsAllSpeakers() {
        val speakers = buildSpeakers()

        every { conferenceDao.insertAll(speakers) } just Runs
        subject.onSpeakersReceivedEvent(SpeakersReceivedEvent(speakers))

        verify{conferenceDao.insertAll(speakers)}
    }

    @Test
    fun onSpeakersReceivedEventItNotifiesSpeakersPersisted() {
        val speakers = buildSpeakers()

        every { conferenceDao.insertAll(speakers) } just Runs
        subject.onSpeakersReceivedEvent(SpeakersReceivedEvent(speakers))

        val speakersPersistedEventCaptor = slot<SpeakersPersistedEvent>()

        verify{eventBus.post(capture(speakersPersistedEventCaptor))}
    }

    @Test
    fun onSessionsReceivedEventItInsertsAllSessions() {
        val sessions = buildSessions()

        every { conferenceDao.insertAll(sessions) } just Runs
        subject.onSessionsReceivedEvent(SessionsReceivedEvent(sessions = sessions))

        verify{conferenceDao.insertAll(sessions)}
    }

    @Test
    fun onSessionsReceivedEventItNotifiesConferenceDataPersisted() {
        val sessions = buildSessions()

        every { conferenceDao.insertAll(sessions) } just Runs
        subject.onSessionsReceivedEvent(SessionsReceivedEvent(sessions))

        val conferenceDataPersistedEventCaptor = slot<ConferenceDataPersistedEvent>()

        verify{eventBus.post(capture(conferenceDataPersistedEventCaptor))}
    }

    private fun buildSpeakers(): Array<Speaker> {
        return arrayOf(Speaker(
                LinkedInProfile = "linkedin",
                Id = "1234",
                LastName = "Smith",
                TwitterLink = "twitter",
                GitHubLink = "github",
                FirstName = "John",
                GravatarUrl = "gravitar",
                Biography = "biography",
                BlogUrl = "blog"
        ))
    }

    private fun buildSessions(): Array<Session> {
        return arrayOf(Session(
                Id = "123",
                Category = "DevOps",
                SessionStartTime = "start time",
                SessionEndTime = "end time",
                SessionType = "session type",
                SessionTime = "session time",
                Title = "title",
                Abstract = "abstract"
        ))
    }
}