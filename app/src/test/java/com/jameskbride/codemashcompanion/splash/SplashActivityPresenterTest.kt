package com.jameskbride.codemashcompanion.splash

import com.jameskbride.codemashcompanion.bus.ConferenceDataPersistedEvent
import com.jameskbride.codemashcompanion.bus.RequestConferenceDataEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class SplashActivityPresenterTest {

    lateinit var eventBus: EventBus
    lateinit var view: SplashActivityView
    lateinit var subject: SplashActivityPresenter

    private var requestConferenceDataEventCalled = false

    @Before
    fun setUp() {
        view = mock(SplashActivityView::class.java)
        eventBus = EventBus.getDefault()
        eventBus.register(this)
        subject = SplashActivityPresenter(eventBus)
        subject.view = view
    }

    @Test
    fun openRegistersWithTheBusOnOpen() {
        eventBus = mock(EventBus::class.java)
        subject = SplashActivityPresenter(eventBus)
        subject.open()

        verify(eventBus).register(subject)
    }

    @Test
    fun openDoesNotRegisterWithTheBusIfAlreadyRegistered() {
        eventBus = mock(EventBus::class.java)
        subject = SplashActivityPresenter(eventBus)
        `when`(eventBus.isRegistered(subject)).thenReturn(true)

        subject.open()

        verify(eventBus, times(0)).register(subject)
    }

    @Test
    fun closeUnregistersWithTheBusOnClose() {
        eventBus = mock(EventBus::class.java)
        subject = SplashActivityPresenter(eventBus)
        `when`(eventBus.isRegistered(subject)).thenReturn(true)

        subject.close()

        verify(eventBus).unregister(subject)
    }

    @Test
    fun closeDoesNotUnregisterWithTheBusIfNotRegistered() {
        eventBus = mock(EventBus::class.java)
        subject = SplashActivityPresenter(eventBus)
        `when`(eventBus.isRegistered(subject)).thenReturn(false)

        subject.close()

        verify(eventBus, times(0)).unregister(subject)
    }

    @Test
    fun requestConferenceDataEventSendsTheConferenceDataRequestEvent() {
        subject.requestConferenceData()

        assertTrue(requestConferenceDataEventCalled)
    }

    @Test
    fun onConferenceDataPersistedNavigatesToTheMainView() {
        subject.onConferenceDataPersistedEvent(ConferenceDataPersistedEvent())

        verify(view).navigateToMain()
    }

    @Subscribe
    fun onRequestConferenceDataEvent(event: RequestConferenceDataEvent) {
        requestConferenceDataEventCalled = true
    }
}