package com.jameskbride.codemashcompanion.main

import org.greenrobot.eventbus.EventBus
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class MainActivityPresenterTest {

    lateinit var eventBus: EventBus
    lateinit var subject: MainActivityPresenter

    @Before
    fun setUp() {
        eventBus = mock(EventBus::class.java)
        subject = MainActivityPresenter()
        subject.eventBus = eventBus
    }

    @Test
    fun itRegistersWithTheBusOnOpen() {
        subject.open()

        verify(eventBus).register(subject)
    }

    @Test
    fun itUnregistersWithTheBusOnClose() {
        subject.close()

        verify(eventBus).unregister(subject)
    }
}