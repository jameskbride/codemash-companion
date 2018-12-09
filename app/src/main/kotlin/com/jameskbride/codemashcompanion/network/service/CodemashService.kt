package com.jameskbride.codemashcompanion.network.service

import com.jameskbride.codemashcompanion.bus.*
import com.jameskbride.codemashcompanion.network.CodemashApi
import com.jameskbride.codemashcompanion.network.adapters.ApiAdapter.Companion.buildRooms
import com.jameskbride.codemashcompanion.network.adapters.ApiAdapter.Companion.buildSessionSpeakers
import com.jameskbride.codemashcompanion.network.adapters.ApiAdapter.Companion.buildTags
import com.jameskbride.codemashcompanion.network.adapters.ApiAdapter.Companion.mapApiSessionsToDomain
import com.jameskbride.codemashcompanion.network.adapters.ApiAdapter.Companion.mapApiSpeakersToDomain
import com.jameskbride.codemashcompanion.network.model.ApiSession
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
               .subscribe (
                       {result -> eventBus.post(SpeakersUpdatedEvent(speakers = mapApiSpeakersToDomain(result)))},
                       {error -> eventBus.post(ConferenceDataRequestError(error))}
               )
    }

    @Subscribe
    fun onSpeakersPersistedEvent(event: SpeakersPersistedEvent) {
        codemashApi.getSessions()
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe (
                        {result -> handleSessionsUpdated(result)},
                        {error -> eventBus.post(ConferenceDataRequestError(error))}
                )
    }

    private fun handleSessionsUpdated(apiSessions: List<ApiSession>) {
        eventBus.post(RoomsUpdatedEvent(conferenceRooms = buildRooms(apiSessions)))
        eventBus.post(TagsUpdatedEvent(tags = buildTags(apiSessions)))
        eventBus.post(SessionSpeakersUpdatedEvent(sessionSpeakers = buildSessionSpeakers(apiSessions)))
        eventBus.post(SessionsUpdatedEvent(sessions = mapApiSessionsToDomain(apiSessions)))
    }
}