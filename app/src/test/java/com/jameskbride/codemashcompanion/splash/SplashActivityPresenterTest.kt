package com.jameskbride.codemashcompanion.splash

import com.jameskbride.codemashcompanion.bus.ConferenceDataPersistedEvent
import com.jameskbride.codemashcompanion.bus.ConferenceDataRequestError
import com.jameskbride.codemashcompanion.bus.NoDataEvent
import com.jameskbride.codemashcompanion.bus.RequestConferenceDataEvent
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.utils.test.buildDefaultSpeakers
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.schedulers.TestScheduler
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SplashActivityPresenterTest {

    lateinit var eventBus: EventBus
    lateinit var view: SplashActivityView
    lateinit var conferenceRepository:ConferenceRepository
    lateinit var subject: SplashActivityPresenter
    lateinit var testScheduler: TestScheduler

    private var requestConferenceDataEventCalled = false

    @Before
    fun setUp() {
        view = mock()
        conferenceRepository = mock()
        testScheduler = TestScheduler()
        eventBus = EventBus.getDefault()
        eventBus.register(this)
        subject = SplashActivityPresenter(eventBus, conferenceRepository, testScheduler, testScheduler)
        subject.view = view
        subject.open()
        val emptySpeakers:Array<FullSpeaker> = arrayOf()
        val emptySpeakersMaybe: Maybe<Array<FullSpeaker>> = Maybe.just(emptySpeakers)
        whenever(conferenceRepository.getSpeakers()).thenReturn(emptySpeakersMaybe)
    }

    @After
    fun tearDown() {
        subject.close()
        eventBus.unregister(this)
    }

    @Test
    fun requestConferenceDataEventSendsTheConferenceDataRequestEvent() {
        subject.requestConferenceData()
        testScheduler.triggerActions()

        assertTrue(requestConferenceDataEventCalled)
    }

    @Test
    fun requestConferenceDataNavigatesToMainWhenDataIsAlreadyPresent() {
        val speakers = buildDefaultSpeakers()
        val emptySpeakersMaybe: Maybe<Array<FullSpeaker>> = Maybe.just(speakers)
        whenever(conferenceRepository.getSpeakers()).thenReturn(emptySpeakersMaybe)

        subject.requestConferenceData()
        testScheduler.triggerActions()

        verify(view).navigateToMain()
    }

    @Test
    fun requestConferenceDataDisplaysAnErrorDialogWhenAnErrorOccursPullingData() {
        whenever(conferenceRepository.getSpeakers()).thenReturn(Maybe.error(Exception("Woops!")))

        subject.requestConferenceData()
        testScheduler.triggerActions()

        verify(view).showErrorDialog()
    }

    @Test
    fun onConferenceDataPersistedNavigatesToTheMainView() {
        subject.onConferenceDataPersistedEvent(ConferenceDataPersistedEvent())

        verify(view).navigateToMain()
    }

    @Test
    fun onConferenceDataRequestErrorDisplaysAnErrorDialog() {
        subject.onConferenceDataRequestError(ConferenceDataRequestError())

        verify(view).showErrorDialog()
    }

    @Test
    fun onNoDataEventNavigatesToMain() {
        eventBus.post(NoDataEvent())

        verify(view).navigateToMain()
    }

    @Subscribe
    fun onRequestConferenceDataEvent(event: RequestConferenceDataEvent) {
        requestConferenceDataEventCalled = true
    }
}