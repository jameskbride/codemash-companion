package com.jameskbride.codemashcompanion.splash

import com.jameskbride.codemashcompanion.bus.ConferenceDataPersistedEvent
import com.jameskbride.codemashcompanion.bus.RequestConferenceDataEvent
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SplashActivityPresenterTest {

    lateinit var eventBus: EventBus
    lateinit var view: SplashActivityView
    lateinit var subject: SplashActivityPresenter

    private var requestConferenceDataEventCalled = false

    @Before
    fun setUp() {
        view = mockk()
        eventBus = EventBus.getDefault()
        eventBus.register(this)
        subject = SplashActivityPresenter(eventBus)
        subject.view = view
    }

    @Test
    fun openRegistersWithTheBusOnOpen() {
        eventBus = mockk()
        subject = SplashActivityPresenter(eventBus)
        every{eventBus.isRegistered(subject)} returns false
        every{eventBus.register(subject)} returns Unit
        subject.open()

        verify{eventBus.register(subject)}
    }

    @Test
    fun openDoesNotRegisterWithTheBusIfAlreadyRegistered() {
        eventBus = mockk()
        subject = SplashActivityPresenter(eventBus)
        every{eventBus.isRegistered(subject)} returns true

        subject.open()

        verify(exactly = 0){eventBus.register(subject)}
    }

    @Test
    fun closeUnregistersWithTheBusOnClose() {
        eventBus = mockk()
        subject = SplashActivityPresenter(eventBus)
        every { eventBus.isRegistered(subject)} returns true
        every { eventBus.unregister(subject)} returns Unit

        subject.close()

        verify{eventBus.unregister(subject)}
    }

    @Test
    fun closeDoesNotUnregisterWithTheBusIfNotRegistered() {
        eventBus = mockk()
        subject = SplashActivityPresenter(eventBus)
        every{eventBus.isRegistered(subject)} returns false

        subject.close()

        verify(exactly = 0) {eventBus.unregister(subject)}
    }

    @Test
    fun requestConferenceDataEventSendsTheConferenceDataRequestEvent() {
        subject.requestConferenceData()

        assertTrue(requestConferenceDataEventCalled)
    }

    @Test
    fun onConferenceDataPersistedNavigatesToTheMainView() {
        every{view.navigateToMain()} returns Unit
        subject.onConferenceDataPersistedEvent(ConferenceDataPersistedEvent())

        verify{view.navigateToMain()}
    }

    @Subscribe
    fun onRequestConferenceDataEvent(event: RequestConferenceDataEvent) {
        requestConferenceDataEventCalled = true
    }
}