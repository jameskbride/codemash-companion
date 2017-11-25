package com.jameskbride.codemashcompanion.splash

import com.jameskbride.codemashcompanion.bus.BusAware
import com.jameskbride.codemashcompanion.bus.ConferenceDataPersistedEvent
import com.jameskbride.codemashcompanion.bus.RequestConferenceDataEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

class SplashActivityPresenter @Inject constructor(override val eventBus: EventBus) :BusAware {

    lateinit var view: SplashActivityView

    fun requestConferenceData() {
        eventBus.post(RequestConferenceDataEvent())
    }

    @Subscribe
    fun onConferenceDataPersistedEvent(conferenceDataPersistedEvent: ConferenceDataPersistedEvent) {
        view.navigateToMain()
    }
}

interface SplashActivityView {
    fun navigateToMain()
}