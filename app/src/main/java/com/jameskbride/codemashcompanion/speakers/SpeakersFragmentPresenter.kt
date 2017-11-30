package com.jameskbride.codemashcompanion.speakers

import com.jameskbride.codemashcompanion.bus.BusAware
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class SpeakersFragmentPresenter @Inject constructor(override val eventBus: EventBus): BusAware {
}