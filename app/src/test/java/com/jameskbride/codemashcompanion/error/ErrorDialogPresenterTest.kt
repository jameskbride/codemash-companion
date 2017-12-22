package com.jameskbride.codemashcompanion.error

import com.jameskbride.codemashcompanion.bus.NoDataEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ErrorDialogPresenterTest {

    private lateinit var subject:ErrorDialogPresenter

    private lateinit var eventBus:EventBus

    private var noDataEventFired: Boolean = false

    @Before
    fun setUp() {
        eventBus = EventBus.getDefault()
        subject = ErrorDialogPresenter(eventBus)
        eventBus.register(this)
    }

    @After
    fun tearDown() {
        eventBus.unregister(this)
    }

    @Test
    fun itSendsNoDataEventOnNavigateToMain() {
        subject.navigateToMain()

        assertTrue(noDataEventFired)
    }

    @Subscribe
    fun onNoDataEvent(noDataEvent: NoDataEvent) {
        noDataEventFired = true
    }
}