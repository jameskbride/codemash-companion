package com.jameskbride.codemashcompanion.sessions.detail

import android.support.annotation.StringRes
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.utils.IntentFactory
import io.reactivex.Scheduler
import javax.inject.Inject

class SessionDetailActivityPresenter @Inject constructor(val conferenceRepository: ConferenceRepository,
                                                 val processScheduler: Scheduler,
                                                 val androidScheduler: Scheduler) {

    lateinit var view:SessionDetailActivityView

    fun retrieveSpeakers(session: FullSession) {
        val speakerIds = session.sessionSpeakers.map { it.speakerId }
        conferenceRepository.getSpeakers(speakerIds)
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe (
                        { results -> view.displaySpeakers(results) },
                        { error -> view.displayErrorMessage(R.string.unexpected_error) }
                )
    }

    fun addBookmark(fullSession: FullSession) {
        conferenceRepository.addBookmark(fullSession)
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe(
                        {result -> updateSession(fullSession.Id) },
                        {error -> view.displayErrorMessage(R.string.unexpected_error)}
                )
    }

    private fun updateSession(id: String) {
        conferenceRepository.getSessions(arrayOf(id.toInt()))
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe(
                        {result -> view.configureForSession(result[0])},
                        {error -> view.displayErrorMessage(R.string.unexpected_error)}
                )
    }

    fun removeBookmark(fullSession: FullSession) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

interface SessionDetailActivityView {
    fun displaySpeakers(speakers: Array<FullSpeaker>)
    fun displayErrorMessage(@StringRes message: Int)
    fun configureForSession(session: FullSession)

}