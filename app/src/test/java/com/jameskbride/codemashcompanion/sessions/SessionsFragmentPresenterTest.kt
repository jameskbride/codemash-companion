package com.jameskbride.codemashcompanion.sessions

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.network.Session
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.schedulers.TestScheduler
import org.greenrobot.eventbus.EventBus
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import java.text.SimpleDateFormat
import java.util.*

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
    fun whenSessionsDataIsReceivedThenItIsGroupedByStartTime() {
        val firstStartTime = "2018-01-11T10:15:00"
        val secondStartTime = "2018-01-11T09:15:00"
        val thirdStartTime = "2018-01-12T09:15:00"
        val sessions = arrayOf(
                Session(SessionStartTime = thirdStartTime),
                Session(SessionStartTime = firstStartTime),
                Session(SessionStartTime = secondStartTime),
                Session(SessionStartTime = secondStartTime)
        )

        whenever(conferenceRepository.getSessions()).thenReturn(Maybe.just(sessions))

        subject.requestSessions()
        testScheduler.triggerActions()

        val sessionDataCaptor = argumentCaptor<SessionData>()
        verify(view).onSessionDataRetrieved(sessionDataCaptor.capture())
        assertArrayEquals(sessions, sessionDataCaptor.firstValue.sessions)
    }
}