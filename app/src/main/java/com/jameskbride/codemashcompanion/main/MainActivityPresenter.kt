package com.jameskbride.codemashcompanion.main

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}