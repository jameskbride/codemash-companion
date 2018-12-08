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

        onSessionSpeakersUpdatedEvent(SessionSpeakersUpdatedEvent(sessionSpeakers = buildSessionSpeakers(apiSessions)))
        onTagsUpdatedEvent(TagsUpdatedEvent(tags = buildTags(apiSessions)))
        onRoomsUpdatedEvent(RoomsUpdatedEvent(conferenceRooms = buildRooms(apiSessions)))

        eventBus.post(ConferenceDataPersistedEvent())
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onSessionSpeakersUpdatedEvent(speakersUpdatedEvent: SessionSpeakersUpdatedEvent) {
        val sessionSpeakers = speakersUpdatedEvent.sessionSpeakers
        conferenceDao.insertAll(sessionSpeakers.toTypedArray())
    }

    private fun onSessionsUpdatedEvent(apiSessions: List<ApiSession>) {
        val sessions = mapApiSessionsToDomain(apiSessions)
        conferenceDao.insertAll(sessions)
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onRoomsUpdatedEvent(roomsUpdatedEvent: RoomsUpdatedEvent) {
        conferenceDao.deleteRooms()
        val conferenceRooms = roomsUpdatedEvent.conferenceRooms
        conferenceDao.insertAll(conferenceRooms.toTypedArray())
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onTagsUpdatedEvent(tagsUpdatedEvent: TagsUpdatedEvent) {
        conferenceDao.deleteTags()
        val tags = tagsUpdatedEvent.tags
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