package com.jameskbride.codemashcompanion.sessions.detail

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.data.model.SessionSpeaker
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SessionDetailActivityPresenterTest {

    @Mock private lateinit var conferenceRepository:ConferenceRepository
    @Mock private lateinit var view:SessionDetailActivityImpl
    private lateinit var subject: SessionDetailActivityPresenter

    private lateinit var testScheduler:TestScheduler

    @Before
    fun setUp() {
        initMocks(this)
        testScheduler = TestScheduler()
        subject = SessionDetailActivityPresenter(conferenceRepository, testScheduler, testScheduler)
        subject.view = view
    }

    @Test
    fun itCanRetrieveSpeakers() {
        val fullSession = FullSession(
                sessionSpeakers = listOf(
                        SessionSpeaker(sessionId = 1, speakerId = "1"),
                        SessionSpeaker(sessionId = 1, speakerId = "2")
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
}