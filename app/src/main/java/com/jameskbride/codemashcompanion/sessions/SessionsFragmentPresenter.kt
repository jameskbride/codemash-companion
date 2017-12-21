package com.jameskbride.codemashcompanion.sessions

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSession
import io.reactivex.Scheduler
import javax.inject.Inject

class SessionsFragmentPresenter @Inject constructor(val conferenceRepository: ConferenceRepository,
                                                    val processScheduler: Scheduler,
                                                    val androidScheduler: Scheduler) {
    lateinit var view: SessionsFragmentView

    fun requestSessions() {
        conferenceRepository.getSessions()
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe { results -> notifyView(results) }
    }

    private fun notifyView(results: Array<FullSession>) {
        view.onSessionDataRetrieved(SessionData(results))
    }

    fun navigateToSessionDetail(session: FullSession) {
        view.navigateToSessionDetail(session)
    }

    fun refreshConferenceData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

interface SessionsFragmentView {
    fun onSessionDataRetrieved(sessionsData: SessionData)
    fun navigateToSessionDetail(session: FullSession)
}