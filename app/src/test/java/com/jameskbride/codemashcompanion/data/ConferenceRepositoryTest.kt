package com.jameskbride.codemashcompanion.data

import com.jameskbride.codemashcompanion.bus.*
import com.jameskbride.codemashcompanion.data.model.*
import com.jameskbride.codemashcompanion.network.model.ApiSession
import com.jameskbride.codemashcompanion.network.model.ApiSpeaker
import com.jameskbride.codemashcompanion.network.model.ShortSpeaker
import com.jameskbride.codemashcompanion.utils.test.buildDefaultApiSpeakers
import com.jameskbride.codemashcompanion.utils.test.buildDefaultSpeakers
import com.nhaarman.mockito_kotlin.any
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
    fun onSpeakersReceivedEventItInsertsAllSpeakers() {
        val apiSpeakers = listOf(ApiSpeaker(
                id = "1234",
                firstName = "John",
                lastName = "Smith",
                linkedInProfile = "linkedin",
                twitterLink = "twitter",
                gitHubLink = "github",
                biography = "biography",
                blogUrl = "blog"
        ))

        subject.onSpeakersReceivedEvent(SpeakersReceivedEvent(apiSpeakers))

        val speakersCaptor = argumentCaptor<Array<Speaker>>()
        verify(conferenceDao).insertAll(speakersCaptor.capture())

        val actualSpeakers = speakersCaptor.firstValue
        assertEquals(apiSpeakers.size, actualSpeakers.size)

        val apiSpeaker = apiSpeakers[0]
        val actualSpeaker = actualSpeakers[0]
        assertEquals(apiSpeaker.id, actualSpeaker.Id)
        assertEquals(apiSpeaker.firstName, actualSpeaker.FirstName)
        assertEquals(apiSpeaker.lastName, actualSpeaker.LastName)
        assertEquals(apiSpeaker.linkedInProfile, actualSpeaker.LinkedInProfile)
        assertEquals(apiSpeaker.twitterLink, actualSpeaker.TwitterLink)
        assertEquals(apiSpeaker.gitHubLink, actualSpeaker.GitHubLink)
        assertEquals(apiSpeaker.biography, actualSpeaker.Biography)
        assertEquals(apiSpeaker.blogUrl, actualSpeaker.BlogUrl)
    }

    @Test
    fun onSpeakersReceivedEventItInsertsSpeakersWithTheCorrectedGravatarUrl() {
        val apiSpeakers = listOf(ApiSpeaker(
                gravatarUrl = "//gravitar"
        ))

        subject.onSpeakersReceivedEvent(SpeakersReceivedEvent(apiSpeakers))

        val speakersCaptor = argumentCaptor<Array<Speaker>>()
        verify(conferenceDao).insertAll(speakersCaptor.capture())

        val actualSpeakers = speakersCaptor.firstValue

        val apiSpeaker = apiSpeakers[0]
        val actualSpeaker = actualSpeakers[0]
        assertEquals("http:${apiSpeaker.gravatarUrl}", actualSpeaker.GravatarUrl)
    }

    @Test
    fun onSpeakersReceivedEventItNotifiesSpeakersPersisted() {
        val speakers = buildDefaultApiSpeakers()

        subject.onSpeakersReceivedEvent(SpeakersReceivedEvent(speakers.asList()))

        assertTrue(speakersPersistedEventFired)
    }

    @Test
    fun onSessionsReceivedEventItInsertsAllSessions() {
        val apiSessions:List<ApiSession> = buildApiSessions()

        subject.onSessionsReceivedEvent(SessionsReceivedEvent(sessions = apiSessions))

        val sessionsCaptor = argumentCaptor<Array<Session>>()
        verify(conferenceDao).insertAll(sessionsCaptor.capture())
        val sessions = sessionsCaptor.firstValue

        assertEquals(apiSessions.size, sessions.size)
        val apiSession:ApiSession = apiSessions[0]
        val session: Session = sessions[0]
        assertEquals(apiSession.id.toString(), session.Id)
        assertEquals(apiSession.category, session.Category)
        assertEquals(apiSession.sessionStartTime, session.SessionStartTime)
        assertEquals(apiSession.sessionEndTime, session.SessionEndTime)
        assertEquals(apiSession.sessionType, session.SessionType)
        assertEquals(apiSession.title, session.Title)
        assertEquals(apiSession.abstract, session.Abstract)
    }

    @Test
    fun onSessionsReceivedEventItInsertsAllRooms() {
        val apiSessions = listOf(
                ApiSession(
                    id = 1,
                    rooms = listOf("banyan", "salon e")),
                ApiSession(
                    id = 2,
                    rooms = listOf("banyan", "salon b")
                )
        )

        subject.onSessionsReceivedEvent(SessionsReceivedEvent(sessions = apiSessions))

        val conferenceRoomsCaptor = argumentCaptor<Array<ConferenceRoom>>()
        verify(conferenceDao).insertAll(conferenceRoomsCaptor.capture())

        val rooms = conferenceRoomsCaptor.firstValue
        assertEquals(4, rooms.size)

        assertTrue(rooms.contains(ConferenceRoom(sessionId = "1", name = "banyan")))
        assertTrue(rooms.contains(ConferenceRoom(sessionId = "1", name = "salon e")))
        assertTrue(rooms.contains(ConferenceRoom(sessionId = "2", name = "banyan")))
        assertTrue(rooms.contains(ConferenceRoom(sessionId = "2", name = "salon b")))
    }

    @Test
    fun onSessionsReceivedEventItInsertsAllTags() {
        val apiSessions = listOf(
                ApiSession(id = 1, tags = listOf("tag 1", "tag 2")),
                ApiSession(id = 2, tags = listOf("tag 1", "tag 3"))
        )

        subject.onSessionsReceivedEvent(SessionsReceivedEvent(apiSessions))

        val tagsCaptor = argumentCaptor<Array<Tag>>()
        verify(conferenceDao).insertAll(tagsCaptor.capture())

        val tags = tagsCaptor.firstValue
        assertEquals(4, tags.size)

        assertTrue(tags.contains(Tag(sessionId = "1", name = "tag 1")))
        assertTrue(tags.contains(Tag(sessionId = "1", name = "tag 2")))
        assertTrue(tags.contains(Tag(sessionId = "2", name = "tag 1")))
        assertTrue(tags.contains(Tag(sessionId = "2", name = "tag 3")))
    }

    @Test
    fun onSessionsReceivedEventItInsertsAllSessionSpeakers() {
        val apiSessions = listOf(
                ApiSession(
                        shortSpeakers = listOf(
                            ShortSpeaker(id = "1"),
                            ShortSpeaker(id = "2")
                        ),
                        id = 1
                ),
                ApiSession(
                        shortSpeakers = listOf(
                                ShortSpeaker(id = "3"),
                                ShortSpeaker(id = "4")
                        ),
                        id = 2
                )
        )

        subject.onSessionsReceivedEvent(SessionsReceivedEvent(apiSessions))

        val sessionSpeakerCaptor = argumentCaptor<Array<SessionSpeaker>>()
        verify(conferenceDao).insertAll(sessionSpeakerCaptor.capture())

        val sessionSpeakers = sessionSpeakerCaptor.firstValue
        assertEquals(4, sessionSpeakers.size)

        assertTrue((sessionSpeakers.contains(SessionSpeaker(sessionId = "1", speakerId = "1"))))
        assertTrue((sessionSpeakers.contains(SessionSpeaker(sessionId = "1", speakerId = "2"))))
        assertTrue((sessionSpeakers.contains(SessionSpeaker(sessionId = "2", speakerId = "3"))))
        assertTrue((sessionSpeakers.contains(SessionSpeaker(sessionId = "2", speakerId = "4"))))
    }

    @Test
    fun onSessionsReceivedEventItNotifiesConferenceDataPersisted() {
        val sessions = buildApiSessions()

        subject.onSessionsReceivedEvent(SessionsReceivedEvent(sessions))

        assertTrue(conferenceDataPersistedEventFired)
    }

    @Test
    fun getSpeakersReturnsTheSpeakersFromTheDao() {
        val speakers = buildDefaultSpeakers()

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
    fun onSessionUpdatedEvent(sessionUpdatedEvent: SessionUpdatedEvent) {
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