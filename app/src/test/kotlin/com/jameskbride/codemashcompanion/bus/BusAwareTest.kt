package com.jameskbride.codemashcompanion.bus

import org.greenrobot.eventbus.EventBus
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class BusAwareTest {
    lateinit var eventBus: EventBus
    lateinit var subject: LocalBusAware

    @Before
    fun setUp() {
        eventBus = EventBus.getDefault()
        subject = LocalBusAware(eventBus)
    }

    @Test
    fun openRegistersWithTheBusOnOpen() {
        eventBus = Mockito.mock(EventBus::class.java)
        subject = LocalBusAware(eventBus)
        subject.open()

        Mockito.verify(eventBus).register(subject)
    }

    @Test
    fun openDoesNotRegisterWithTheBusIfAlreadyRegistered() {
        eventBus = Mockito.mock(EventBus::class.java)
        subject = LocalBusAware(eventBus)
        Mockito.`when`(eventBus.isRegistered(subject)).thenReturn(true)

        subject.open()

        Mockito.verify(eventBus, Mockito.times(0)).register(subject)
    }

    @Test
    fun closeUnregistersWithTheBusOnClose() {
        eventBus = Mockito.mock(EventBus::class.java)
        subject = LocalBusAware(eventBus)
        Mockito.`when`(eventBus.isRegistered(subject)).thenReturn(true)

        subject.close()

        Mockito.verify(eventBus).unregister(subject)
    }

    @Test
    fun closeDoesNotUnregisterWithTheBusIfNotRegistered() {
        eventBus = Mockito.mock(EventBus::class.java)
        subject = LocalBusAware(eventBus)
        Mockito.`when`(eventBus.isRegistered(subject)).thenReturn(false)

        subject.close()

        Mockito.verify(eventBus, Mockito.times(0)).unregister(subject)
    }
}

class LocalBusAware (override val eventBus: EventBus) : BusAware