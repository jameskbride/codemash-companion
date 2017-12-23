package com.jameskbride.codemashcompanion.sessions.detail

import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.bus.SessionUpdatedEvent
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.Bookmark
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.data.model.SessionSpeaker
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.schedulers.TestScheduler
import org.greenrobot.eventbus.EventBus
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SessionDetailActivityPresenterTest {

    @Mock private lateinit var conferenceRepository:ConferenceRepository
    @Mock private lateinit var view:SessionDetailActivityView
    private lateinit var subject: SessionDetailActivityPresenter

    private lateinit var testScheduler:TestScheduler
    private lateinit var eventBus:EventBus

    @Before
    fun setUp() {
        initMocks(this)
        testScheduler = TestScheduler()
        eventBus = EventBus.getDefault()
        subject = SessionDetailActivityPresenter(conferenceRepository, testScheduler, testScheduler, eventBus)
        subject.open()
        subject.view = view
    }

    @After
    fun tearDown() {
        subject.close()
    }

    @Test
    fun itCanRetrieveSpeakers() {
        val fullSession = FullSession(
                sessionSpeakers = listOf(
                        SessionSpeaker(sessionId = "1", speakerId = "1"),
                        SessionSpeaker(sessionId = "1", speakerId = "2")
                )
        )

        val speakers = arrayOf(FullSpeaker("1"), FullSpeaker("2"))
        val speakerObservable = Maybe.just(speakers)
        whenever(conferenceRepository.getSpeakers(anyList<String>())).thenReturn(speakerObservable)

        subject.retrieveSpeakers(fullSession)
        testScheduler.triggerActions()

        val speakerIdsCaptor = argumentCaptor<List<String>>()
        verify(conferenceRepository).getSpeakers(speakerIdsCaptor.capture())

        assertTrue(speakerIdsCaptor.firstValue.containsAll(listOf("1", "2")))
        verify(view).displaySpeakers(speakers)
    }

    @Test
    fun itDisplaysAnErrorMessageMessageWhenAnErrorOccursRetrievingSpeakers() {
        val fullSession = FullSession(
                sessionSpeakers = listOf(
                        SessionSpeaker(sessionId = "1", speakerId = "1"),
                        SessionSpeaker(sessionId = "1", speakerId = "2")
                )
        )
        whenever(conferenceRepository.getSpeakers(any())).thenReturn(Maybe.error(Exception("Woops!")))

        subject.retrieveSpeakers(fullSession)
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
}