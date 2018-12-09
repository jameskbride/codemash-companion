package com.jameskbride.codemashcompanion.sessions

import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.bus.ConferenceDataPersistedEvent
import com.jameskbride.codemashcompanion.bus.ConferenceDataRequestError
import com.jameskbride.codemashcompanion.bus.RequestConferenceDataEvent
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.sessions.list.SessionData
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.schedulers.TestScheduler
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SessionsFragmentPresenterTest {

    @Mock private lateinit var conferenceRepository:ConferenceRepository
    @Mock private lateinit var view:SessionsFragmentView

    private lateinit var subject:SessionsFragmentPresenter

    private lateinit var testScheduler:TestScheduler
    private lateinit var eventBus:EventBus
    private lateinit var sessionsRetriever:SessionsRetriever

    private var requestConferenceDataEventFired: Boolean = false

    @Before
    fun setUp() {
        initMocks(this)
        testScheduler = TestScheduler()
        sessionsRetriever = AllSessionRetriever(conferenceRepository)
        eventBus = EventBus.getDefault()
        eventBus.register(this)

        subject = SessionsFragmentPresenter(sessionsRetriever, testScheduler, testScheduler, eventBus)
        subject.open()
        subject.view = view
    }

    @After
    fun tearDown() {
        subject.close()
        eventBus.unregister(this)
    }

    @Test
    fun whenSessionsDataIsReceivedThenItIsPassedToTheView() {
        val firstStartTime = "2018-01-11T10:15:00"
        val sessions:Array<FullSession> = arrayOf(
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
    fun whenAnErrorOccursOnSessionDataReceivedThenAMessageIsDisplayedOnTheView() {
        whenever(conferenceRepository.getSessions()).thenReturn(Maybe.error(Exception("Oops!")))

        subject.requestSessions()
        testScheduler.triggerActions()

        verify(view).displayErrorMessage(R.string.unexpected_error)
    }

    @Test
    fun itDelegatesToTheViewWhenNavigatingToASession() {
        val firstStartTime = "2018-01-11T10:15:00"
        val session = FullSession(SessionStartTime = firstStartTime)

        subject.navigateToSessionDetail(session)

        verify(view).navigateToSessionDetail(session)
    }

    @Test
    fun itCanRefreshConferenceData() {
        subject.refreshConferenceData()

        assertTrue(requestConferenceDataEventFired)
    }

    @Test
    fun onConferenceDataPersistedItRequestsSessions() {
        val firstStartTime = "2018-01-11T10:15:00"
        val sessions:Array<FullSession> = arrayOf(
                FullSession(SessionStartTime = firstStartTime)
        )

        whenever(conferenceRepository.getSessions()).thenReturn(Maybe.just(sessions))

        eventBus.post(ConferenceDataPersistedEvent())

        testScheduler.triggerActions()

        val sessionDataCaptor = argumentCaptor<SessionData>()
        verify(view).onSessionDataRetrieved(sessionDataCaptor.capture())
        assertArrayEquals(sessions, sessionDataCaptor.firstValue.sessions)
    }

    @Test
    fun onConferenceDataRequestErrorItNotifiesTheViewToDisplayAMessage() {
        eventBus.post(ConferenceDataRequestError(Throwable()))

        verify(view).displayErrorMessage(R.string.could_not_refresh)
    }

    @Test
    fun onConferenceDataRequestErrorItNotifiesTheViewToStopRefreshing() {
        eventBus.post(ConferenceDataRequestError(Throwable()))

        verify(view).stopRefreshing()
    }

    @Subscribe
    fun onRequestConferenceDataEvent(requestConferenceDataEvent: RequestConferenceDataEvent) {
        this.requestConferenceDataEventFired = true
    }
}