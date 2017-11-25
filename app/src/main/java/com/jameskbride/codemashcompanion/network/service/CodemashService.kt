package com.jameskbride.codemashcompanion.network.service

import com.jameskbride.codemashcompanion.bus.*
import com.jameskbride.codemashcompanion.network.CodemashApi
import io.reactivex.Scheduler
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject


class CodemashService @Inject constructor(private val codemashApi: CodemashApi,
                                          override val eventBus: EventBus,
                                          private val processScheduler: Scheduler,
                                          private val androidScheduler: Scheduler) : BusAware {

    @Subscribe
    fun onRequestConferenceDataEvent(event: RequestConferenceDataEvent) {
       codemashApi.getSpeakers()
               .subscribeOn(processScheduler)
               .observeOn(androidScheduler)
               .subscribe {result -> eventBus.post(SpeakersReceivedEvent(speakers = result))}
    }

    @Subscribe
    fun onSpeakersPersistedEvent(event: SpeakersPersistedEvent) {
        codemashApi.getSessions()
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe {result -> eventBus.post(SessionsReceivedEvent(sessions = result))}
    }
}