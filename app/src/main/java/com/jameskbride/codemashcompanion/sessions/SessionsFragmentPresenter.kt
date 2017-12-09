package com.jameskbride.codemashcompanion.sessions

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.network.Session
import io.reactivex.Scheduler
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.LinkedHashMap

class SessionsFragmentPresenter @Inject constructor(val eventBus: EventBus,
                                                    val conferenceRepository: ConferenceRepository,
                                                    val processScheduler: Scheduler,
                                                    val androidScheuler: Scheduler) {
    lateinit var view: SessionsFragmentView

    fun requestSessions() {
        conferenceRepository.getSessions()
                .subscribeOn(processScheduler)
                .observeOn(androidScheuler)
                .subscribe { results -> notifyView(results) }
    }

    private fun notifyView(results: Array<Session>?) {
        val groupedSpeakers = results?.groupBy { speaker ->
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
            dateFormatter.parse(speaker.SessionStartTime)
        }

        var ordered = linkedMapOf<Date, Array<Session>?>()
        groupedSpeakers?.keys?.forEach {
            ordered[it] = groupedSpeakers!![it]?.toTypedArray()
        }

        view.onSessionDataRetrieved(ordered)
    }
}

interface SessionsFragmentView {
    fun onSessionDataRetrieved(sessionsData: LinkedHashMap<Date, Array<Session>?>)
}