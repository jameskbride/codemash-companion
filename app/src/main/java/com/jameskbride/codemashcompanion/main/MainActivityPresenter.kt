package com.jameskbride.codemashcompanion.main

import com.jameskbride.codemashcompanion.bus.RequestConferenceDataEvent
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(val eventBus: EventBus) {

    fun open() {
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this)
        }
    }

    fun close() {
        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this)
        }
    }

    fun requestConferenceData() {
        eventBus.post(RequestConferenceDataEvent())
    }
}