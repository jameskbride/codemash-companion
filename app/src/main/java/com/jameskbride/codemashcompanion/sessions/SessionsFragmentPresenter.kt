package com.jameskbride.codemashcompanion.sessions

import com.jameskbride.codemashcompanion.bus.BusAware
import com.jameskbride.codemashcompanion.bus.RequestConferenceDataEvent
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSession
import io.reactivex.Scheduler
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class SessionsFragmentPresenter @Inject constructor(val conferenceRepository: ConferenceRepository,
                                                    val processScheduler: Scheduler,
                                                    val androidScheduler: Scheduler,
                                                    override val eventBus: EventBus): BusAware {
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
        eventBus.post(RequestConferenceDataEvent())
    }
}

interface SessionsFragmentView {
    fun onSessionDataRetrieved(sessionsData: SessionData)
    fun navigateToSessionDetail(session: FullSession)
}