package com.jameskbride.codemashcompanion.speakers

import com.jameskbride.codemashcompanion.bus.BusAware
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class SpeakersFragmentPresenter @Inject constructor(override val eventBus: EventBus, conferenceRepository: ConferenceRepository): BusAware {
    fun requestSpeakerData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}