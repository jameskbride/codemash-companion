package com.jameskbride.codemashcompanion.network.service

import com.jameskbride.codemashcompanion.bus.RequestConferenceDataEvent
import com.jameskbride.codemashcompanion.bus.SpeakersReceivedEvent
import com.jameskbride.codemashcompanion.network.CodemashApi
import io.reactivex.Scheduler
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class CodemashService(private val codemashApi: CodemashApi, private val eventBus: EventBus, private val processScheduler: Scheduler, private val androidScheduler: Scheduler) {

    @Subscribe
    fun onRequestConferenceData(event: RequestConferenceDataEvent) {
       codemashApi.getSpeakers()
               .subscribeOn(processScheduler)
               .observeOn(androidScheduler)
               .subscribe {result -> eventBus.post(SpeakersReceivedEvent(speakers = result.speakers))}
    }
}