package com.jameskbride.codemashcompanion.speakers.detail

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import io.reactivex.Scheduler
import javax.inject.Inject

class SpeakerDetailFragmentPresenter @Inject constructor(
        val conferenceRepository: ConferenceRepository,
        val processScheduler:Scheduler,
        val androidScheduler:Scheduler) {

    lateinit var view:SpeakerDetailFragmentView

    fun retrieveSessions(speaker: FullSpeaker) {
        val sessionIds = speaker.sessionSpeakers.map { it.sessionId }.toTypedArray()
        conferenceRepository.getSessions(sessionIds)
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe { results -> view.displaySessions(results) }
    }
}

interface SpeakerDetailFragmentView {
    fun displaySessions(sessions: Array<FullSession>)
}