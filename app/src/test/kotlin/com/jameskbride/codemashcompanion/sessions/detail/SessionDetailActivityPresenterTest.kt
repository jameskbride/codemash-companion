package com.jameskbride.codemashcompanion.sessions.detail

import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.bus.SessionUpdatedEvent
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.Bookmark
import com.jameskbride.codemashcompanion.data.model.ConferenceRoom
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.rooms.MapFinder
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.schedulers.TestScheduler
import org.greenrobot.eventbus.EventBus
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SessionDetailActivityPresenterTest {

    @Mock private lateinit var conferenceRepository:ConferenceRepository
    @Mock private lateinit var view:SessionDetailActivityView
    @Mock private lateinit var mapFinder:MapFinder

    private lateinit var subject: SessionDetailActivityPresenter

    private lateinit var testScheduler:TestScheduler
    private lateinit var eventBus:EventBus

    @Before
    fun setUp() {
        initMocks(this)
        testScheduler = TestScheduler()
        eventBus = EventBus.getDefault()
        subject = SessionDetailActivityPresenter(conferenceRepository, testScheduler, testScheduler, eventBus, mapFinder)
        subject.open()
        subject.view = view
    }

    @After
    fun tearDown() {
        subject.close()
    }

    @Test
    fun itCanRetrieveSpeakers() {
        val fullSession = FullSession(Id = "123")

        val speakers = arrayOf(FullSpeaker("1"), FullSpeaker("2"))
        val speakerObservable = Maybe.just(speakers)
        whenever(conferenceRepository.getSpeakersBySession("123")).thenReturn(speakerObservable)

        subject.retrieveSpeakers(fullSession.Id)
        testScheduler.triggerActions()

        val sessionIdCaptor = argumentCaptor<String>()
        verify(conferenceRepository).getSpeakersBySession(sessionIdCaptor.capture())

        assertEquals("123", sessionIdCaptor.firstValue)
        verify(view).displaySpeakers(speakers)
    }

    @Test
    fun itDisplaysAnErrorMessageMessageWhenAnErrorOccursRetrievingSpeakers() {
        val fullSession = FullSession(Id = "123")
        whenever(conferenceRepository.getSpeakersBySession("123")).thenReturn(Maybe.error(Exception("Woops!")))

        subject.retrieveSpeakers(fullSession.Id)
        testScheduler.triggerActions()

        verify(view).displayErrorMessage(R.string.unexpected_error)
    }

    @Test
    fun itCanAddABookMark() {
        val fullSession = FullSession(Id = "1")

        subject.addBookmark(fullSession)
        testScheduler.triggerActions()

        verify(conferenceRepository).addBookmark(any())
    }

    @Test
    fun itCanRemoveABookmark() {
        val bookmark = Bookmark(sessionId = "1")
        val fullSession = FullSession(
                Id = "1",
                bookmarks = listOf(bookmark)
        )

        subject.removeBookmark(fullSession)
        testScheduler.triggerActions()

        val bookmarkCaptor = argumentCaptor<Bookmark>()
        verify(conferenceRepository).removeBookmark(bookmarkCaptor.capture())
        assertEquals("1", bookmark.sessionId)
    }

    @Test
    fun onSessionUpdatedEventItUpdatesTheView() {
        val fullSession = FullSession()
        val fullSessionObservable = Maybe.just(arrayOf(fullSession))

        whenever(conferenceRepository.getSessions(arrayOf("1"))).thenReturn(fullSessionObservable)

        subject.updateSession(SessionUpdatedEvent("1"))
        testScheduler.triggerActions()

        verify(conferenceRepository).getSessions(any())
        verify(view).configureForSession(fullSession)
    }

    @Test
    fun onNavigateToMapItTellsTheViewWhatMapToDisplay() {
        val conferenceRooms = listOf<ConferenceRoom>()
        whenever(mapFinder.findMap(conferenceRooms)).thenReturn(R.drawable.indigo_bay)

        subject.navigateToMap(conferenceRooms)

        verify(mapFinder).findMap(conferenceRooms)
        verify(view).navigateToMap(R.drawable.indigo_bay)
    }
}