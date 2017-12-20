package com.jameskbride.codemashcompanion.speakers.detail

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import io.reactivex.Scheduler
import javax.inject.Inject

class SpeakerDetailFragmentPresenter @Inject constructor(
        val conferenceRepository: ConferenceRepository,
        val processScheduler:Scheduler,
        val androidScheduler:Scheduler) {

    lateinit var view:SpeakerDetailFragmentView

    fun retrieveSessions(speaker: FullSpeaker) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

interface SpeakerDetailFragmentView {

}