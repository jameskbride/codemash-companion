package com.jameskbride.codemashcompanion.speakers.detail

import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.data.model.SessionSpeaker
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SpeakerDetailFragmentPresenterTest {

    @Mock private lateinit var view:SpeakerDetailFragmentView
    @Mock private lateinit var conferenceRepository:ConferenceRepository

    private lateinit var subject:SpeakerDetailFragmentPresenter

    private lateinit var testScheduler:TestScheduler

    @Before
    fun setUp() {
        initMocks(this)
        testScheduler = TestScheduler()
        subject = SpeakerDetailFragmentPresenter(conferenceRepository, testScheduler, testScheduler)
        subject.view = view
    }

    @Test
    fun itCanRetrieveTheSessions() {
        val sessionSpeakers = listOf(
                SessionSpeaker(sessionId = "1", speakerId = "1"),
                SessionSpeaker(sessionId = "2", speakerId = "1")
        )
        val fullSpeaker = FullSpeaker(
                Id = "1",
                sessionSpeakers = sessionSpeakers
        )

        val sessions = arrayOf(
                FullSession("1"),
                FullSession("2")
        )

        val sessionsObservable = Maybe.just(sessions)

        whenever(conferenceRepository.getSessions(arrayOf("1", "2"))).thenReturn(sessionsObservable)

        subject.retrieveSessions(fullSpeaker)
        testScheduler.triggerActions()

        val sessionsCaptor = argumentCaptor<Array<FullSession>>()
        verify(view).displaySessions(sessionsCaptor.capture())

        assertArrayEquals(sessions, sessionsCaptor.firstValue)
    }

    @Test
    fun itDisplaysAnErrorMessageWhenSessionsCannotBeRetrieved() {
        whenever(conferenceRepository.getSessions(any())).thenReturn(Maybe.error(Exception("Woops!")))

        val sessionSpeakers = listOf(
                SessionSpeaker(sessionId = "1", speakerId = "1"),
                SessionSpeaker(sessionId = "2", speakerId = "1")
        )
        val fullSpeaker = FullSpeaker(
                Id = "1",
                sessionSpeakers = sessionSpeakers
        )

        subject.retrieveSessions(fullSpeaker)
        testScheduler.triggerActions()

        verify(view).displayErrorMessage(R.string.unexpected_error)
    }
}