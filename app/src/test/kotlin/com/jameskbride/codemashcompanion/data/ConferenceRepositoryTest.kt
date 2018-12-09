package com.jameskbride.codemashcompanion.data

import com.jameskbride.codemashcompanion.bus.*
import com.jameskbride.codemashcompanion.data.model.*
import com.jameskbride.codemashcompanion.network.model.ApiSession
import com.jameskbride.codemashcompanion.utils.test.buildDefaultFullSpeakers
import com.jameskbride.codemashcompanion.utils.test.buildDefaultSessions
import com.jameskbride.codemashcompanion.utils.test.buildDefaultSpeakers
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class ConferenceRepositoryTest {

    @Mock private lateinit var conferenceDao: ConferenceDao

    private lateinit var subject: ConferenceRepository

    private lateinit var eventBus: EventBus

    private var sessionUpdatedEventFired: Boolean = false
    private var speakersPersistedEventFired: Boolean = false
    private var conferenceDataPersistedEventFired: Boolean = false

    @Before
    fun setUp() {
        initMocks(this)
        eventBus = EventBus.getDefault()
        eventBus.register(this)
        subject = ConferenceRepository(conferenceDao, eventBus)
        subject.open()
    }

    @After
    fun tearDown() {
        subject.close()
        eventBus.unregister(this)
    }

    @Test
    fun onSpeakersUpdatedEventItInsertsAllSpeakers() {
        val speakers = listOf(Speaker(
                Id = "1234",
                FirstName = "John",
                LastName = "Smith",
                LinkedInProfile = "linkedin",
                TwitterLink = "twitter",
                GitHubLink = "github",
                Biography = "biography",
                BlogUrl = "blog"
        ))

        subject.onSpeakersUpdatedEvent(SpeakersUpdatedEvent(speakers))

        val speakersCaptor = argumentCaptor<Array<Speaker>>()
        verify(conferenceDao).insertAll(speakersCaptor.capture())

        val actualSpeakers = speakersCaptor.firstValue
        assertEquals(speakers.size, actualSpeakers.size)

        val speaker = speakers[0]
        val actualSpeaker = actualSpeakers[0]
        assertEquals(speaker.Id, actualSpeaker.Id)
        assertEquals(speaker.FirstName, actualSpeaker.FirstName)
        assertEquals(speaker.LastName, actualSpeaker.LastName)
        assertEquals(speaker.LinkedInProfile, actualSpeaker.LinkedInProfile)
        assertEquals(speaker.TwitterLink, actualSpeaker.TwitterLink)
        assertEquals(speaker.GitHubLink, actualSpeaker.GitHubLink)
        assertEquals(speaker.Biography, actualSpeaker.Biography)
        assertEquals(speaker.BlogUrl, actualSpeaker.BlogUrl)
        assertEquals(speaker.GravatarUrl, actualSpeaker.GravatarUrl)
    }

    @Test
    fun onSpeakersUpdatedEventItNotifiesSpeakersPersisted() {
        val speakers = buildDefaultSpeakers()

        subject.onSpeakersUpdatedEvent(SpeakersUpdatedEvent(speakers.asList()))

        assertTrue(speakersPersistedEventFired)
    }

    @Test
    fun onTagsUpdatedEventItDeletesAllTags() {
        subject.onTagsUpdatedEvent(TagsUpdatedEvent(tags = listOf(Tag(sessionId = "sessionId", name = "some tag"))))

        verify(conferenceDao).deleteTags()
    }

    @Test
    fun onRoomsUpdatedEventItDeletesAllRooms() {
        subject.onRoomsUpdatedEvent(RoomsUpdatedEvent(conferenceRooms = listOf(ConferenceRoom(sessionId = "sessionId", name = "some room"))))

        verify(conferenceDao).deleteRooms()
    }

    @Test
    fun onSessionsUpdatedEventItInsertsAllSessions() {
        val sessions = buildDefaultSessions(1)
        subject.onSessionsUpdatedEvent(SessionsUpdatedEvent(sessions = sessions.toList()))

        val sessionsCaptor = argumentCaptor<Array<Session>>()
        verify(conferenceDao).insertAll(sessionsCaptor.capture())
        val actualSessions = sessionsCaptor.firstValue

        assertEquals(sessions.size, actualSessions.size)
        val expectedSession:Session = sessions[0]
        val actualSession: Session = actualSessions[0]
        assertEquals(expectedSession.Id, actualSession.Id)
        assertEquals(expectedSession.Category, actualSession.Category)
        assertEquals(expectedSession.SessionStartTime, actualSession.SessionStartTime)
        assertEquals(expectedSession.SessionEndTime, actualSession.SessionEndTime)
        assertEquals(expectedSession.SessionType, actualSession.SessionType)
        assertEquals(expectedSession.Title, actualSession.Title)
        assertEquals(expectedSession.Abstract, actualSession.Abstract)
    }

    @Test
    fun onSessionsUpdatedEventItNotifiesConferenceDataPersisted() {
        subject.onSessionsUpdatedEvent(SessionsUpdatedEvent(listOf()))

        assertTrue(conferenceDataPersistedEventFired)
    }

    @Test
    fun onRoomsUpdatedEventItInsertsAllRooms() {
        subject.onRoomsUpdatedEvent(RoomsUpdatedEvent(conferenceRooms = listOf(ConferenceRoom(sessionId = "sessionId", name = "some room"))))

        val conferenceRoomsCaptor = argumentCaptor<Array<ConferenceRoom>>()
        verify(conferenceDao).insertAll(conferenceRoomsCaptor.capture())

        val rooms = conferenceRoomsCaptor.firstValue
        assertEquals(1, rooms.size)

        assertTrue(rooms.contains(ConferenceRoom(sessionId = "sessionId", name = "some room")))
    }

    @Test
    fun onTagsUpdatedEventItInsertsAllTags() {
        subject.onTagsUpdatedEvent(TagsUpdatedEvent(tags = listOf(Tag(sessionId = "sessionId", name = "some tag"))))

        val tagsCaptor = argumentCaptor<Array<Tag>>()
        verify(conferenceDao).insertAll(tagsCaptor.capture())

        val tags = tagsCaptor.firstValue
        assertEquals(1, tags.size)

        assertTrue(tags.contains(Tag(sessionId = "sessionId", name = "some tag")))
    }

    @Test
    fun onSessionSpeakersUpdatedEventItInsertsAllSessionSpeakers() {
        subject.onSessionSpeakersUpdatedEvent(SessionSpeakersUpdatedEvent(sessionSpeakers = listOf(SessionSpeaker(sessionId = "sessionId", speakerId = "1234"))))

        val sessionSpeakerCaptor = argumentCaptor<Array<SessionSpeaker>>()
        verify(conferenceDao).insertAll(sessionSpeakerCaptor.capture())

        val sessionSpeakers = sessionSpeakerCaptor.firstValue
        assertEquals(1, sessionSpeakers.size)

        assertTrue((sessionSpeakers.contains(SessionSpeaker(sessionId = "sessionId", speakerId = "1234"))))
    }

    @Test
    fun getSpeakersReturnsTheSpeakersFromTheDao() {
        val speakers = buildDefaultFullSpeakers()

        val maybe = Maybe.just(speakers)
        whenever(conferenceDao.getSpeakers()).thenReturn(maybe)

        val result = subject.getSpeakers()

        assertEquals(maybe, result)
    }

    @Test
    fun getSpeakersByIdsReturnsTheSpeakersFromTheDao() {
        val speakers = arrayOf(FullSpeaker("1"), FullSpeaker("2"))

        val maybe = Maybe.just(speakers)
        val speakerIds = listOf("1", "2")
        whenever(conferenceDao.getSpeakers(speakerIds.toTypedArray())).thenReturn(maybe)

        val result = subject.getSpeakers(speakerIds)
        assertEquals(maybe, result)
    }

    @Test
    fun getSpeakersBySessionIdReturnsTheSpeakersFromTheDao() {
        val speakers = arrayOf(FullSpeaker("1"), FullSpeaker("2"))

        val maybe = Maybe.just(speakers)
        whenever(conferenceDao.getSpeakersBySession("1")).thenReturn(maybe)

        val result = subject.getSpeakersBySession("1")
        assertEquals(maybe, result)
    }

    @Test
    fun getSessionsReturnsTheSessionsFromTheDao() {
        val sessions:Array<FullSession> = arrayOf(FullSession(
                Id = "123",
                Category = "DevOps",
                SessionStartTime = "start time",
                SessionEndTime = "end time",
                SessionType = "session type",
                SessionTime = "session time",
                Title = "title",
                Abstract = "abstract"
        ))

        val maybe = Maybe.just(sessions)
        whenever(conferenceDao.getSessions()).thenReturn(maybe)

        var result = subject.getSessions()

        assertEquals(maybe, result)
    }

    @Test
    fun getSessionsByIdsReturnsTheSessionsFromDao() {
        val sessions:Array<FullSession> = arrayOf(
                FullSession(
                    Id = "1"
                ),
                FullSession(
                   Id = "2"
                )
        )

        val maybe = Maybe.just(sessions)
        val ids = arrayOf("1", "2")
        whenever(conferenceDao.getSessions(ids)).thenReturn(maybe)

        var result = subject.getSessions(ids)

        assertEquals(maybe, result)
    }

    @Test
    fun itCanAddABookmark() {
        val session = FullSession(Id = "1")

        subject.addBookmark(session)

        val bookmarkCaptor = argumentCaptor<Bookmark>()
        verify(conferenceDao).insert(bookmarkCaptor.capture())

        assertEquals(session.Id, bookmarkCaptor.firstValue.sessionId)

        assertTrue(sessionUpdatedEventFired)
    }

    @Test
    fun itCanRemoveABookmark() {
        val bookmark = Bookmark(sessionId = "1")
        val session = FullSession(Id = "1",
                bookmarks = listOf(bookmark))

        subject.removeBookmark(session.bookmarks[0])

        val bookmarkCaptor = argumentCaptor<Bookmark>()
        verify(conferenceDao).delete(bookmarkCaptor.capture())

        assertEquals(bookmark.sessionId, bookmarkCaptor.firstValue.sessionId)

        assertTrue(sessionUpdatedEventFired)
    }

    @Test
    fun itCanRetrieveBookmarkedSessions() {
        val expectedResponse = Maybe.just(arrayOf(FullSession()))

        whenever(conferenceDao.getBookmarkedSessions()).thenReturn(expectedResponse)

        val actualResponse = subject.getBookmarkedSessions()

        assertSame(expectedResponse, actualResponse)
        verify(conferenceDao).getBookmarkedSessions()
    }

    private fun buildApiSessions(): List<ApiSession> {
        return listOf(ApiSession(
                id = 123,
                category = "DevOps",
                sessionStartTime = "start time",
                sessionEndTime = "end time",
                sessionType = "session type",
                sessionTime = "session time",
                title = "title",
                abstract = "abstract",
                rooms = listOf("banyan", "salon e")
        ))
    }

    @Subscribe
    fun onSessionUpdatedEvent(sessionUpdatedEvent: SessionBookmarkUpdated) {
        sessionUpdatedEventFired = true
    }

    @Subscribe
    fun onSpeakersPersistedEvent(speakersPersistedEvent: SpeakersPersistedEvent) {
        speakersPersistedEventFired = true
    }

    @Subscribe
    fun onConferenceDataPersistedEvent(conferenceDataPersistedEvent: ConferenceDataPersistedEvent) {
        conferenceDataPersistedEventFired = true
    }
}