package com.jameskbride.codemashcompanion.error

import com.jameskbride.codemashcompanion.bus.BusAware
import com.jameskbride.codemashcompanion.bus.NoDataEvent
import org.greenrobot.eventbus.EventBus

class ErrorDialogPresenter(override val eventBus: EventBus) : BusAware {
    fun navigateToMain() {
        eventBus.post(NoDataEvent())
    }
}