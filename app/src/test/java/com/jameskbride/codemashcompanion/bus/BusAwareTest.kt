package com.jameskbride.codemashcompanion.bus

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.greenrobot.eventbus.EventBus
import org.junit.Before
import org.junit.Test

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
        eventBus = mockk()
        subject = LocalBusAware(eventBus)
        every { eventBus.isRegistered(subject) } returns false
        every {eventBus.register(subject)} returns Unit
        subject.open()

        verify {eventBus.register(subject)}
    }

    @Test
    fun openDoesNotRegisterWithTheBusIfAlreadyRegistered() {
        eventBus = mockk()
        subject = LocalBusAware(eventBus)
        every{eventBus.isRegistered(subject)} returns true

        subject.open()

       verify(exactly = 0) {eventBus.register(subject)}
    }

    @Test
    fun closeUnregistersWithTheBusOnClose() {
        eventBus = mockk()
        subject = LocalBusAware(eventBus)
        every { eventBus.unregister(subject) } returns Unit
        every { eventBus.isRegistered(subject)} returns true

        subject.close()

        verify{eventBus.unregister(subject)}
    }

    @Test
    fun closeDoesNotUnregisterWithTheBusIfNotRegistered() {
        eventBus = mockk()
        subject = LocalBusAware(eventBus)
        every{eventBus.isRegistered(subject)} returns false

        subject.close()

        verify(exactly = 0){eventBus.unregister(subject)}
    }
}

class LocalBusAware (override val eventBus: EventBus) : BusAware