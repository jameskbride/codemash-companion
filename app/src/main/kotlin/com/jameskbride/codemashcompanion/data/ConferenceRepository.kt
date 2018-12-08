package com.jameskbride.codemashcompanion.data

import com.jameskbride.codemashcompanion.bus.*
import com.jameskbride.codemashcompanion.data.model.*
import com.jameskbride.codemashcompanion.network.adapters.ApiAdapter.Companion.mapApiSessionRoomsToDomain
import com.jameskbride.codemashcompanion.network.adapters.ApiAdapter.Companion.mapApiSessionSpeakersToDomain
import com.jameskbride.codemashcompanion.network.adapters.ApiAdapter.Companion.mapApiSessionTagsToDomain
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
        val sessions = mapApiSessionsToDomain(apiSessions)
        conferenceDao.deleteTags()
        conferenceDao.deleteRooms()
        conferenceDao.insertAll(sessions)
        val sessionSpeakers = buildSessionSpeakers(apiSessions)
        conferenceDao.insertAll(sessionSpeakers)

        var conferenceRooms = buildRooms(apiSessions)
        conferenceDao.insertAll(conferenceRooms.toTypedArray())

        val tags = buildTags(apiSessions)
        conferenceDao.insertAll(tags.toTypedArray())

        eventBus.post(ConferenceDataPersistedEvent())
    }

    fun getSpeakers(): Maybe<Array<FullSpeaker>> {
        return conferenceDao.getSpeakers()
    }

    private fun buildSessionSpeakers(sessions:List<ApiSession>):Array<SessionSpeaker> {
        val sessionSpeakers = sessions.map { session ->
            mapApiSessionSpeakersToDomain(session)
        }
        return sessionSpeakers.flatten().toTypedArray()
    }

    private fun buildRooms(apiSessions: List<ApiSession>): MutableList<ConferenceRoom> {
        val allRooms = apiSessions.map { session ->
            mapApiSessionRoomsToDomain(session)
        }
        return allRooms.flatten().toMutableList()
    }

    private fun buildTags(apiSessions: List<ApiSession>): MutableList<Tag> {
        val allTags = apiSessions.map { session ->
            mapApiSessionTagsToDomain(session)
        }

        return allTags.flatten().toMutableList()
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