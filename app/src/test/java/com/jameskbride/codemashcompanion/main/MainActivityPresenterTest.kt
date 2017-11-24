package com.jameskbride.codemashcompanion.main

import com.jameskbride.codemashcompanion.bus.RequestConferenceDataEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class MainActivityPresenterTest {

    lateinit var eventBus: EventBus
    lateinit var subject: MainActivityPresenter

    var requestConferenceDataEventCalled = false

    @Before
    fun setUp() {
        eventBus = EventBus.getDefault()
        eventBus.register(this)
        subject = MainActivityPresenter(eventBus)
    }

    @Test
    fun openRegistersWithTheBusOnOpen() {
        eventBus = mock(EventBus::class.java)
        subject = MainActivityPresenter(eventBus)
        subject.open()

        verify(eventBus).register(subject)
    }

    @Test
    fun openDoesNotRegisterWithTheBusIfAlreadyRegistered() {
        eventBus = mock(EventBus::class.java)
        subject = MainActivityPresenter(eventBus)
        `when`(eventBus.isRegistered(subject)).thenReturn(true)

        subject.open()

        verify(eventBus, times(0)).register(subject)
    }

    @Test
    fun closeUnregistersWithTheBusOnClose() {
        eventBus = mock(EventBus::class.java)
        subject = MainActivityPresenter(eventBus)
        `when`(eventBus.isRegistered(subject)).thenReturn(true)

        subject.close()

        verify(eventBus).unregister(subject)
    }

    @Test
    fun closeDoesNotUnregisterWithTheBusIfNotRegistered() {
        eventBus = mock(EventBus::class.java)
        subject = MainActivityPresenter(eventBus)
        `when`(eventBus.isRegistered(subject)).thenReturn(false)

        subject.close()

        verify(eventBus, times(0)).unregister(subject)
    }

    @Test
    fun requestConferenceDataEventSendsTheConferenceDataRequestEvent() {
        subject.requestConferenceData()

        assertTrue(requestConferenceDataEventCalled)
    }

    @Subscribe
    fun onRequestConferenceDataEvent(event: RequestConferenceDataEvent) {
        requestConferenceDataEventCalled = true
    }
}