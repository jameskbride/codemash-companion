package com.jameskbride.codemashcompanion.speakers

import com.jameskbride.codemashcompanion.bus.BusAware
import com.jameskbride.codemashcompanion.bus.ConferenceDataPersistedEvent
import com.jameskbride.codemashcompanion.bus.RequestConferenceDataEvent
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import io.reactivex.Scheduler
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

class SpeakersFragmentPresenter @Inject constructor(override val eventBus: EventBus,
                                                    val conferenceRepository: ConferenceRepository,
                                                    val processScheduler: Scheduler,
                                                    val androidScheduler: Scheduler): BusAware {

    lateinit var view:SpeakersFragmentView

    fun requestSpeakerData() {
        conferenceRepository.getSpeakers()
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe {speakers -> view.onSpeakerDataRetrieved(speakers)}
    }

    fun navigateToDetails(speakers: Array<FullSpeaker>, index: Int) {
        view.navigateToDetails(speakers, index)
    }

    fun refreshConferenceData() {
        eventBus.post(RequestConferenceDataEvent())
    }

    @Subscribe
    fun onConferenceDataPersistedEvent(conferenceDataPersistedEvent: ConferenceDataPersistedEvent) {
        requestSpeakerData()
    }
}

interface SpeakersFragmentView {
    fun onSpeakerDataRetrieved(speakers: Array<FullSpeaker>)
    fun navigateToDetails(speakers: Array<FullSpeaker>, index: Int)
}