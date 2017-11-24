package com.jameskbride.codemashcompanion.bus

import org.greenrobot.eventbus.EventBus

interface BusAware {

    val eventBus: EventBus

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
}