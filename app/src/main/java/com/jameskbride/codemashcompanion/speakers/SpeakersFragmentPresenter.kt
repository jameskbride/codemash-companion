package com.jameskbride.codemashcompanion.speakers

import com.jameskbride.codemashcompanion.bus.BusAware
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.network.Speaker
import io.reactivex.Scheduler
import org.greenrobot.eventbus.EventBus
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
}

interface SpeakersFragmentView {
    fun onSpeakerDataRetrieved(speakers: Array<Speaker>)
}