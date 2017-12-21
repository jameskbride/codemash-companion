package com.jameskbride.codemashcompanion.splash

import com.jameskbride.codemashcompanion.bus.BusAware
import com.jameskbride.codemashcompanion.bus.ConferenceDataPersistedEvent
import com.jameskbride.codemashcompanion.bus.RequestConferenceDataEvent
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import io.reactivex.Scheduler
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

class SplashActivityPresenter @Inject constructor(override val eventBus: EventBus,
                                                  val conferenceRepository: ConferenceRepository,
                                                  val processScheduler: Scheduler,
                                                  val androidScheduler: Scheduler) :BusAware {

    lateinit var view: SplashActivityView

    fun requestConferenceData() {
        conferenceRepository.getSpeakers()
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe { result ->
                    if (result.isEmpty()) {
                        eventBus.post(RequestConferenceDataEvent())
                    } else {
                        onConferenceDataPersistedEvent(ConferenceDataPersistedEvent())
                    }
                }
    }

    @Subscribe
    fun onConferenceDataPersistedEvent(conferenceDataPersistedEvent: ConferenceDataPersistedEvent) {
        view.navigateToMain()
    }
}

interface SplashActivityView {
    fun navigateToMain()
}