package com.jameskbride.codemashcompanion.speakers

import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.bus.ConferenceDataPersistedEvent
import com.jameskbride.codemashcompanion.bus.ConferenceDataRequestError
import com.jameskbride.codemashcompanion.bus.RequestConferenceDataEvent
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.sessions.SessionData
import com.jameskbride.codemashcompanion.utils.test.buildDefaultSpeakers
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.schedulers.TestScheduler
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SpeakersFragmentPresenterTest {

    @Mock private lateinit var conferenceRepository:ConferenceRepository
    @Mock private lateinit var view:SpeakersFragmentView

    private lateinit var subject:SpeakersFragmentPresenter

    private lateinit var testScheduler: TestScheduler
    private lateinit var eventBus:EventBus
    private var requestConferenceDataEventFired: Boolean = false

    @Before
    fun setUp() {
        initMocks(this)
        testScheduler = TestScheduler()
        eventBus = EventBus.getDefault()
        eventBus.register(this)
        subject = SpeakersFragmentPresenter(eventBus, conferenceRepository, testScheduler, testScheduler)
        subject.open()
        subject.view = view
    }

    @After
    fun tearDown() {
        eventBus.unregister(this)
        subject.close()
    }

    @Test
    fun whenSpeakerDataIsReceivedThenTheDataIsPassedToTheView() {
        val speakers = buildDefaultSpeakers()

        whenever(conferenceRepository.getSpeakers()).thenReturn(Maybe.just(speakers))

        subject.requestSpeakerData()

        testScheduler.triggerActions()

        verify(view).onSpeakerDataRetrieved(speakers)
    }

    @Test
    fun whenAnErrorOccursOnSpeakerDataReceivedThenAMessageIsDisplayedOnTheView() {
        whenever(conferenceRepository.getSpeakers()).thenReturn(Maybe.error(Exception("Oops!")))

        subject.requestSpeakerData()
        testScheduler.triggerActions()

        verify(view).displayErrorMessage(R.string.unexpected_error)
    }

    @Test
    fun itDelegatesToTheViewToNavigateToDetails() {
        val speakers = buildDefaultSpeakers()

        subject.navigateToDetails(speakers, 0)

        verify(view).navigateToDetails(speakers, 0)
    }

    @Test
    fun itCanRefreshConferenceData() {
        subject.refreshConferenceData()

        Assert.assertTrue(requestConferenceDataEventFired)
    }

    @Test
    fun onConferenceDataPersistedItRequestsSessions() {
        val speakers = buildDefaultSpeakers()

        whenever(conferenceRepository.getSpeakers()).thenReturn(Maybe.just(speakers))

        eventBus.post(ConferenceDataPersistedEvent())

        testScheduler.triggerActions()

        val sessionDataCaptor = argumentCaptor<Array<FullSpeaker>>()
        verify(view).onSpeakerDataRetrieved(sessionDataCaptor.capture())
        Assert.assertArrayEquals(speakers, sessionDataCaptor.firstValue)
    }

    @Test
    fun onConferenceDataRequestErrorItNotifiesTheViewToDisplayAMessage() {
        eventBus.post(ConferenceDataRequestError())

        verify(view).displayErrorMessage(R.string.could_not_refresh)
    }

    @Test
    fun onConferenceDataRequestErrorItNotifiesTheViewToStopRefreshing() {
        eventBus.post(ConferenceDataRequestError())

        verify(view).stopRefreshing()
    }

    @Subscribe
    fun onRequestConferenceDataEvent(requestConferenceDataEvent: RequestConferenceDataEvent) {
        this.requestConferenceDataEventFired = true
    }
}