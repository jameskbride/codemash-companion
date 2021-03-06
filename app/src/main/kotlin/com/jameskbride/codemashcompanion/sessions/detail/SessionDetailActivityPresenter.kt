package com.jameskbride.codemashcompanion.sessions.detail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.bus.BusAware
import com.jameskbride.codemashcompanion.bus.SessionBookmarkUpdated
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.ConferenceRoom
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.rooms.MapFinder
import io.reactivex.Scheduler
import io.reactivex.Single
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

class SessionDetailActivityPresenter @Inject constructor(
        val conferenceRepository: ConferenceRepository,
        val processScheduler: Scheduler,
        val androidScheduler: Scheduler,
        override val eventBus: EventBus,
        val mapFinder: MapFinder = MapFinder()):BusAware {

    lateinit var view:SessionDetailActivityView

    fun retrieveSpeakers(sessionId: String) {
        conferenceRepository.getSpeakersBySession(sessionId)
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
    fun onSessionBookmarkUpdated(sessionBookmarkUpdated: SessionBookmarkUpdated) {
        val sessionId = sessionBookmarkUpdated.sessionId
        retrieveSession(sessionId)
    }

    fun retrieveSession(sessionId: String) {
        conferenceRepository.getSessions(arrayOf(sessionId))
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe(
                        { result -> view.configureForSession(result[0]) },
                        { error -> view.displayErrorMessage(R.string.unexpected_error) }
                )
    }

    fun removeBookmark(fullSession: FullSession) {
        Single.fromCallable {
            conferenceRepository.removeBookmark(fullSession.bookmarks[0])
        }.subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe { result ->  }
    }

    fun navigateToMap(conferenceRooms: List<ConferenceRoom>) {
        view.navigateToMap(mapFinder.findMap(conferenceRooms))
    }
}

interface SessionDetailActivityView {
    fun displaySpeakers(speakers: Array<FullSpeaker>)
    fun displayErrorMessage(@StringRes message: Int)
    fun configureForSession(session: FullSession)
    fun navigateToMap(@DrawableRes mapId: Int)

}