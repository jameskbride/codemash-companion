package com.jameskbride.codemashcompanion.data

import com.jameskbride.codemashcompanion.bus.*
import com.jameskbride.codemashcompanion.data.model.Bookmark
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import io.reactivex.Maybe
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class ConferenceRepository @Inject constructor(private val conferenceDao: ConferenceDao, override val eventBus: EventBus): BusAware {

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onSpeakersUpdatedEvent(speakersUpdatedEvent: SpeakersUpdatedEvent) {
        conferenceDao.deleteSpeakers()
        val speakers = speakersUpdatedEvent.speakers
        conferenceDao.insertAll(speakers.toTypedArray())
        eventBus.post(SpeakersPersistedEvent())
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onSessionSpeakersUpdatedEvent(speakersUpdatedEvent: SessionSpeakersUpdatedEvent) {
        conferenceDao.deleteSessionSpeakers()
        val sessionSpeakers = speakersUpdatedEvent.sessionSpeakers
        conferenceDao.insertAll(sessionSpeakers.toTypedArray())
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onSessionsUpdatedEvent(sessionsUpdatedEvent: SessionsUpdatedEvent) {
        conferenceDao.deleteSessions()
        val sessions = sessionsUpdatedEvent.sessions
        conferenceDao.insertAll(sessions.toTypedArray())
        eventBus.post(ConferenceDataPersistedEvent())
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

        eventBus.post(SessionBookmarkUpdated(fullSession.Id))
    }

    fun removeBookmark(bookmark: Bookmark) {
        conferenceDao.delete(bookmark)
        eventBus.post(SessionBookmarkUpdated(bookmark.sessionId))
    }

    fun getSpeakersBySession(sessionId: String): Maybe<Array<FullSpeaker>> {
        return conferenceDao.getSpeakersBySession(sessionId)
    }

    fun getBookmarkedSessions(): Maybe<Array<FullSession>> {
        return conferenceDao.getBookmarkedSessions()
    }
}