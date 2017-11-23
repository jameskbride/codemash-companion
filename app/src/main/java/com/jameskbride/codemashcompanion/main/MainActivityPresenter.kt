package com.jameskbride.codemashcompanion.main

import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

public class MainActivityPresenter {

    @Inject
    lateinit var eventBus: EventBus

    fun open() {
        eventBus.register(this)
    }

    fun close() {
        eventBus.unregister(this)
    }
}