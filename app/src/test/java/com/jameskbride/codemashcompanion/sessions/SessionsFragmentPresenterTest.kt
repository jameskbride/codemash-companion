package com.jameskbride.codemashcompanion.sessions

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.network.Session
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.schedulers.TestScheduler
import org.greenrobot.eventbus.EventBus
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
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
        val sessions = arrayOf(
                Session(SessionStartTime = firstStartTime),
                Session(SessionStartTime = secondStartTime),
                Session(SessionStartTime = secondStartTime)
        )

        whenever(conferenceRepository.getSessions()).thenReturn(Maybe.just(sessions))

        subject.requestSessions()
        testScheduler.triggerActions()

        val sessionsCaptor = argumentCaptor<LinkedHashMap<Date, Array<Session>>>()
        verify(view).onSessionDataRetrieved(sessionsCaptor.capture())

        val result = sessionsCaptor.firstValue
        assertEquals(2, result.size)

        val firstStartDatetime = dateFormatter.parse(firstStartTime)
        assertEquals(1, result[firstStartDatetime]?.size)

        val secondStartDatetime = dateFormatter.parse(secondStartTime)
        assertEquals(2, result[secondStartDatetime]?.size)
    }
}