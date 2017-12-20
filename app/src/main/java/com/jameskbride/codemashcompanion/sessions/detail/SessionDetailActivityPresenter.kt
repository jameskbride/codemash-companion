package com.jameskbride.codemashcompanion.sessions.detail

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.Speaker
import com.jameskbride.codemashcompanion.utils.IntentFactory
import io.reactivex.Scheduler
import javax.inject.Inject

class SessionDetailActivityPresenter @Inject constructor(val conferenceRepository: ConferenceRepository,
                                                 val processScheduler: Scheduler,
                                                 val androidScheduler: Scheduler, intentFactory: IntentFactory = IntentFactory()) {

    lateinit var view:SessionDetailActivityView

    fun retrieveSpeakers(session: FullSession) {
        val speakerIds = session.sessionSpeakers.map { it.speakerId }
        conferenceRepository.getSpeakers(speakerIds)
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe { results -> view.displaySpeakers(results) }
    }
}

interface SessionDetailActivityView {
    fun displaySpeakers(speakers: Array<Speaker>)

}