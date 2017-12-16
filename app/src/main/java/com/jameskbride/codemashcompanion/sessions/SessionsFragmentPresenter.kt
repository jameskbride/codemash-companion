package com.jameskbride.codemashcompanion.sessions

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.Session
import io.reactivex.Scheduler
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class SessionsFragmentPresenter @Inject constructor(val eventBus: EventBus,
                                                    val conferenceRepository: ConferenceRepository,
                                                    val processScheduler: Scheduler,
                                                    val androidScheduler: Scheduler) {
    lateinit var view: SessionsFragmentView

    fun requestSessions() {
        conferenceRepository.getSessions()
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe { results -> notifyView(results) }
    }

    private fun notifyView(results: Array<Session>) {
        view.onSessionDataRetrieved(SessionData(results))
    }
}

interface SessionsFragmentView {
    fun onSessionDataRetrieved(sessionsData: SessionData)
}