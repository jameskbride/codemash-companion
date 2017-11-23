package com.jameskbride.codemashcompanion.main

import org.greenrobot.eventbus.EventBus
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class MainActivityPresenterTest {

    lateinit var eventBus: EventBus
    lateinit var subject: MainActivityPresenter

    @Before
    fun setUp() {
        eventBus = mock(EventBus::class.java)
        subject = MainActivityPresenter(eventBus)
    }

    @Test
    fun itRegistersWithTheBusOnOpen() {
        subject.open()

        verify(eventBus).register(subject)
    }

    @Test
    fun itDoesNotRegisterWithTheBusIfAlreadyRegistered() {
        `when`(eventBus.isRegistered(subject)).thenReturn(true)

        subject.open()

        verify(eventBus, times(0)).register(subject)
    }

    @Test
    fun itUnregistersWithTheBusOnClose() {
        `when`(eventBus.isRegistered(subject)).thenReturn(true)

        subject.close()

        verify(eventBus).unregister(subject)
    }

    @Test
    fun itDoesNotUnregisterWithTheBusIfNotRegistered() {
        `when`(eventBus.isRegistered(subject)).thenReturn(false)

        subject.close()

        verify(eventBus, times(0)).unregister(subject)
    }
}