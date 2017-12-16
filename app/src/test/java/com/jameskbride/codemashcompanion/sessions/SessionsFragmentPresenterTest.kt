package com.jameskbride.codemashcompanion.sessions

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.Session
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.schedulers.TestScheduler
import org.greenrobot.eventbus.EventBus
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SessionsFragmentPresenterTest {

    @Mock private lateinit var conferenceRepository:ConferenceRepository
    @Mock private lateinit var eventBus:EventBus
    @Mock private lateinit var view:SessionsFragmentView

    private lateinit var subject:SessionsFragmentPresenter

    private lateinit var testScheduler:TestScheduler

    @Before
    fun setUp() {
        initMocks(this)
        testScheduler = TestScheduler()

        subject = SessionsFragmentPresenter(eventBus, conferenceRepository, testScheduler, testScheduler)
        subject.view = view
    }

    @Test
    fun whenSessionsDataIsReceivedThenItIsPassedToTheView() {
        val firstStartTime = "2018-01-11T10:15:00"
        val sessions:Array<FullSession?> = arrayOf(
                FullSession(SessionStartTime = firstStartTime)
        )

        whenever(conferenceRepository.getSessions()).thenReturn(Maybe.just(sessions))

        subject.requestSessions()
        testScheduler.triggerActions()

        val sessionDataCaptor = argumentCaptor<SessionData>()
        verify(view).onSessionDataRetrieved(sessionDataCaptor.capture())
        assertArrayEquals(sessions, sessionDataCaptor.firstValue.sessions)
    }

    @Test
    fun itDelegatesToTheViewWhenNavigatingToASession() {
        val firstStartTime = "2018-01-11T10:15:00"
        val session = FullSession(SessionStartTime = firstStartTime)

        subject.navigateToSessionDetail(session)

        verify(view).navigateToSessionDetail(session)

    }
}