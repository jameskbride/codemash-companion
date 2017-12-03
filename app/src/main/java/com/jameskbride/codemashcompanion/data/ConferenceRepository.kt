package com.jameskbride.codemashcompanion.data

import com.jameskbride.codemashcompanion.bus.*
import com.jameskbride.codemashcompanion.network.Speaker
import io.reactivex.Maybe
import io.reactivex.Observable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class ConferenceRepository @Inject constructor(private val conferenceDao: ConferenceDao, override val eventBus: EventBus): BusAware {

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onSpeakersReceivedEvent(speakersReceivedEvent: SpeakersReceivedEvent) {
        var correctedSpeakers = speakersReceivedEvent.speakers
        correctedSpeakers.forEach{
            it.GravatarUrl ?: ""
            it.GravatarUrl = "http:${it.GravatarUrl}"
        }
        conferenceDao.insertAll(correctedSpeakers)
        eventBus.post(SpeakersPersistedEvent())
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onSessionsReceivedEvent(sessionsReceivedEvent: SessionsReceivedEvent) {
        conferenceDao.insertAll(sessionsReceivedEvent.sessions)
        eventBus.post(ConferenceDataPersistedEvent())
    }

    fun getSpeakers(): Maybe<Array<Speaker>> {
        return conferenceDao.getSpeakers()
    }
}