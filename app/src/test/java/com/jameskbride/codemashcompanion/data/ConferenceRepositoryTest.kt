package com.jameskbride.codemashcompanion.data

import com.jameskbride.codemashcompanion.bus.ConferenceDataPersistedEvent
import com.jameskbride.codemashcompanion.bus.SessionsReceivedEvent
import com.jameskbride.codemashcompanion.bus.SpeakersPersistedEvent
import com.jameskbride.codemashcompanion.bus.SpeakersReceivedEvent
import com.jameskbride.codemashcompanion.network.Session
import com.jameskbride.codemashcompanion.network.Speaker
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Maybe
import org.greenrobot.eventbus.EventBus
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ConferenceRepositoryTest {

    private lateinit var conferenceDao: ConferenceDao
    private lateinit var eventBus: EventBus
    private lateinit var subject: ConferenceRepository

    @Before
    fun setUp() {
        conferenceDao = mock()
        eventBus = mock()
        subject = ConferenceRepository(conferenceDao, eventBus)
    }

    @Test
    fun onSpeakersReceivedEventItInsertsAllSpeakers() {
        val speakers = buildSpeakers()

        subject.onSpeakersReceivedEvent(SpeakersReceivedEvent(speakers))
        val speakersCaptor = argumentCaptor<Array<Speaker>>()
        verify(conferenceDao).insertAll(speakersCaptor.capture())
        val actualSpeakers = speakersCaptor.firstValue
        assertEquals("http://gravitar", actualSpeakers[0].GravatarUrl)
    }

    @Test
    fun onSpeakersReceivedEventItNotifiesSpeakersPersisted() {
        val speakers = buildSpeakers()

        subject.onSpeakersReceivedEvent(SpeakersReceivedEvent(speakers))

        val speakersPersistedEventCaptor = argumentCaptor<SpeakersPersistedEvent>()

        verify(eventBus).post(speakersPersistedEventCaptor.capture())
    }

    @Test
    fun onSessionsReceivedEventItInsertsAllSessions() {
        val sessions = buildSessions()

        subject.onSessionsReceivedEvent(SessionsReceivedEvent(sessions = sessions))

        verify(conferenceDao).insertAll(sessions)
    }

    @Test
    fun onSessionsReceivedEventItNotifiesConferenceDataPersisted() {
        val sessions = buildSessions()

        subject.onSessionsReceivedEvent(SessionsReceivedEvent(sessions))

        val conferenceDataPersistedEventCaptor = argumentCaptor<ConferenceDataPersistedEvent>()

        verify(eventBus).post(conferenceDataPersistedEventCaptor.capture())
    }

    @Test
    fun getSpeakersReturnsTheSpeakersFromTheDao() {
        val speakers = buildSpeakers()

        val maybe = Maybe.just(speakers)
        whenever(conferenceDao.getSpeakers()).thenReturn(maybe)

        val result = subject.getSpeakers()

        assertEquals(maybe, result)
    }

    private fun buildSpeakers(): Array<Speaker> {
        return arrayOf(Speaker(
                LinkedInProfile = "linkedin",
                Id = "1234",
                LastName = "Smith",
                TwitterLink = "twitter",
                GitHubLink = "github",
                FirstName = "John",
                GravatarUrl = "//gravitar",
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