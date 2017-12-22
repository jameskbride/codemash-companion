package com.jameskbride.codemashcompanion.splash

import com.jameskbride.codemashcompanion.bus.*
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import io.reactivex.Scheduler
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

class SplashActivityPresenter @Inject constructor(override val eventBus: EventBus,
                                                  val conferenceRepository: ConferenceRepository,
                                                  val processScheduler: Scheduler,
                                                  val androidScheduler: Scheduler) : BusAware {

    lateinit var view: SplashActivityView

    fun requestConferenceData() {
        conferenceRepository.getSpeakers()
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe(
                        { result ->
                            if (result.isEmpty()) {
                                eventBus.post(RequestConferenceDataEvent())
                            } else {
                                onConferenceDataPersistedEvent(ConferenceDataPersistedEvent())
                            }
                        },
                        { error ->
                            view.showErrorDialog()
                        }
                )
    }

    @Subscribe
    fun onConferenceDataPersistedEvent(conferenceDataPersistedEvent: ConferenceDataPersistedEvent) {
        view.navigateToMain()
    }

    @Subscribe
    fun onConferenceDataRequestError(conferenceDataRequestError: ConferenceDataRequestError) {
        view.showErrorDialog()
    }

    @Subscribe
    fun onNoDataEvent(noDataEvent: NoDataEvent) {
        view.navigateToMain()
    }
}

interface SplashActivityView {
    fun navigateToMain()
    fun showErrorDialog()
}