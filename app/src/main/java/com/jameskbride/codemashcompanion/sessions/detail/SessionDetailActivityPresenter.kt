package com.jameskbride.codemashcompanion.sessions.detail

import android.support.annotation.StringRes
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.bus.BusAware
import com.jameskbride.codemashcompanion.bus.SessionUpdatedEvent
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import io.reactivex.Scheduler
import io.reactivex.Single
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

class SessionDetailActivityPresenter @Inject constructor(
        val conferenceRepository: ConferenceRepository,
        val processScheduler: Scheduler,
        val androidScheduler: Scheduler,
        override val eventBus: EventBus):BusAware {

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
        Single.fromCallable {
            conferenceRepository.addBookmark(fullSession)
        }.subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe {result -> }
    }

    @Subscribe
    fun updateSession(sessionUpdatedEvent: SessionUpdatedEvent) {
        conferenceRepository.getSessions(arrayOf(sessionUpdatedEvent.sessionId.toInt()))
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe(
                        {result -> view.configureForSession(result[0])},
                        {error -> view.displayErrorMessage(R.string.unexpected_error)}
                )
    }

    fun removeBookmark(fullSession: FullSession) {
        Single.fromCallable {
            conferenceRepository.removeBookmark(fullSession)
        }.subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe { result ->  }
    }
}

interface SessionDetailActivityView {
    fun displaySpeakers(speakers: Array<FullSpeaker>)
    fun displayErrorMessage(@StringRes message: Int)
    fun configureForSession(session: FullSession)

}