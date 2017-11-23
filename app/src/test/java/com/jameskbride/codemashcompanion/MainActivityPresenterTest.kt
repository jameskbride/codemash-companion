package com.jameskbride.codemashcompanion

import org.greenrobot.eventbus.EventBus
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class MainActivityPresenterTest {

    @Test
    fun itRegistersWithTheBusOnOpen() {
        val eventBus = mock(EventBus::class.java);
        val presenter = MainActivityPresenter()
        presenter.eventBus = eventBus

        presenter.open()

        verify(eventBus).register(presenter)
    }
}