package com.jameskbride.codemashcompanion.sessions

import android.support.annotation.StringRes
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.bus.BusAware
import com.jameskbride.codemashcompanion.bus.ConferenceDataPersistedEvent
import com.jameskbride.codemashcompanion.bus.ConferenceDataRequestError
import com.jameskbride.codemashcompanion.bus.RequestConferenceDataEvent
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.sessions.list.SessionData
import io.reactivex.Scheduler
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
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
                .subscribe(
                        { results -> notifyView(results) },
                        {error -> view.displayErrorMessage(R.string.unexpected_error)}
                )
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

    @Subscribe
    fun onConferenceDataPersistedEvent(conferenceDataPersistedEvent: ConferenceDataPersistedEvent) {
        requestSessions()
    }

    @Subscribe
    fun onConferenceDataRequestError(conferenceDataRequestError: ConferenceDataRequestError) {
        view.stopRefreshing()
        view.displayErrorMessage(R.string.could_not_refresh)
    }
}

interface SessionsFragmentView {
    fun onSessionDataRetrieved(sessionsData: SessionData)
    fun navigateToSessionDetail(session: FullSession)
    fun displayErrorMessage(@StringRes message: Int)
    fun stopRefreshing()
}