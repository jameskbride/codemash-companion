package com.jameskbride.codemashcompanion.data

import com.jameskbride.codemashcompanion.bus.BusAware
import com.jameskbride.codemashcompanion.bus.SpeakersPersistedEvent
import com.jameskbride.codemashcompanion.bus.SpeakersReceivedEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class ConferenceRepository @Inject constructor(private val conferenceDao: ConferenceDao, override val eventBus: EventBus): BusAware {

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onSpeakersReceivedEvent(speakersReceivedEvent: SpeakersReceivedEvent) {
        conferenceDao.insertAll(speakersReceivedEvent.speakers)
        eventBus.post(SpeakersPersistedEvent())
    }
}