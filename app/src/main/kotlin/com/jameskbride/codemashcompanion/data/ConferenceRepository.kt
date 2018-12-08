package com.jameskbride.codemashcompanion.data

import com.jameskbride.codemashcompanion.bus.*
import com.jameskbride.codemashcompanion.data.model.Bookmark
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.network.adapters.ApiAdapter.Companion.buildRooms
import com.jameskbride.codemashcompanion.network.adapters.ApiAdapter.Companion.buildSessionSpeakers
import com.jameskbride.codemashcompanion.network.adapters.ApiAdapter.Companion.buildTags
import com.jameskbride.codemashcompanion.network.adapters.ApiAdapter.Companion.mapApiSessionsToDomain
import com.jameskbride.codemashcompanion.network.adapters.ApiAdapter.Companion.mapApiSpeakersToDomain
import com.jameskbride.codemashcompanion.network.model.ApiSession
import io.reactivex.Maybe
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class ConferenceRepository @Inject constructor(private val conferenceDao: ConferenceDao, override val eventBus: EventBus): BusAware {

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onSpeakersReceivedEvent(speakersReceivedEvent: SpeakersReceivedEvent) {
        val apiSpeakers = speakersReceivedEvent.speakers
        val speakers = mapApiSpeakersToDomain(apiSpeakers)
        conferenceDao.insertAll(speakers)
        eventBus.post(SpeakersPersistedEvent())
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onSessionsReceivedEvent(sessionsReceivedEvent: SessionsReceivedEvent) {
        val apiSessions = sessionsReceivedEvent.sessions

        onSessionsUpdatedEvent(apiSessions)

        val sessionSpeakers = buildSessionSpeakers(apiSessions)
        conferenceDao.insertAll(sessionSpeakers)

        onTagsUpdatedEvent(apiSessions)
        onRoomsUpdatedEvent(apiSessions)

        eventBus.post(ConferenceDataPersistedEvent())
    }

    private fun onSessionsUpdatedEvent(apiSessions: List<ApiSession>) {
        val sessions = mapApiSessionsToDomain(apiSessions)
        conferenceDao.insertAll(sessions)
    }

    private fun onRoomsUpdatedEvent(apiSessions: List<ApiSession>) {
        conferenceDao.deleteRooms()
        var conferenceRooms = buildRooms(apiSessions)
        conferenceDao.insertAll(conferenceRooms.toTypedArray())
    }

    private fun onTagsUpdatedEvent(apiSessions: List<ApiSession>) {
        conferenceDao.deleteTags()
        val tags = buildTags(apiSessions)
        conferenceDao.insertAll(tags.toTypedArray())
    }

    fun getSpeakers(): Maybe<Array<FullSpeaker>> {
        return conferenceDao.getSpeakers()
    }

    fun getSessions(): Maybe<Array<FullSession>> {
        return conferenceDao.getSessions()
    }

    fun getSpeakers(ids: List<String>):Maybe<Array<FullSpeaker>> {
        return conferenceDao.getSpeakers(ids.toTypedArray())
    }

    fun getSessions(ids: Array<String>): Maybe<Array<FullSession>> {
        return conferenceDao.getSessions(ids)
    }

    fun addBookmark(fullSession: FullSession) {
        conferenceDao.insert(Bookmark(sessionId = fullSession.Id))

        eventBus.post(SessionUpdatedEvent(fullSession.Id))
    }

    fun removeBookmark(bookmark: Bookmark) {
        conferenceDao.delete(bookmark)
        eventBus.post(SessionUpdatedEvent(bookmark.sessionId))
    }

    fun getSpeakersBySession(sessionId: String): Maybe<Array<FullSpeaker>> {
        return conferenceDao.getSpeakersBySession(sessionId)
    }

    fun getBookmarkedSessions(): Maybe<Array<FullSession>> {
        return conferenceDao.getBookmarkedSessions()
    }
}