package com.jameskbride.codemashcompanion

import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class MainActivityPresenter() {

    @Inject
    lateinit var eventBus: EventBus

    fun open() {
        eventBus.register(this)
    }
}