package com.jameskbride.codemashcompanion.main

import com.jameskbride.codemashcompanion.bus.BusAware
import com.jameskbride.codemashcompanion.bus.RequestConferenceDataEvent
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(override val eventBus: EventBus) :BusAware {

    fun requestConferenceData() {
        eventBus.post(RequestConferenceDataEvent())
    }
}