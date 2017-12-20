package com.jameskbride.codemashcompanion.data

import com.jameskbride.codemashcompanion.bus.*
import com.jameskbride.codemashcompanion.data.model.*
import com.jameskbride.codemashcompanion.network.model.ApiSession
import com.jameskbride.codemashcompanion.network.model.ApiSpeaker
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

    private fun mapApiSpeakersToDomain(apiSpeakers: List<ApiSpeaker>): Array<Speaker> {
        return apiSpeakers.map {
            Speaker(
                    Id = it.id,
                    FirstName = it.firstName,
                    LastName = it.lastName,
                    LinkedInProfile = it.linkedInProfile,
                    TwitterLink = it.twitterLink,
                    GitHubLink = it.gitHubLink,
                    GravatarUrl = "http:${it.gravatarUrl}",
                    Biography = it.biography,
                    BlogUrl = it.blogUrl
            )
        }.toTypedArray()
    }

    fun getSpeakers(): Maybe<Array<FullSpeaker>> {
        return conferenceDao.getSpeakers()
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onSessionsReceivedEvent(sessionsReceivedEvent: SessionsReceivedEvent) {
        val apiSessions = sessionsReceivedEvent.sessions
        val sessions = mapApiSessionsToDomain(apiSessions)
        conferenceDao.insertAll(sessions)
        val sessionSpeakers = buildSessionSpeakers(apiSessions)
        conferenceDao.insertAll(sessionSpeakers)

        var conferenceRooms = buildRooms(apiSessions)
        conferenceDao.insertAll(conferenceRooms.toTypedArray())

        val tags = buildTags(apiSessions)
        conferenceDao.insertAll(tags.toTypedArray())

        eventBus.post(ConferenceDataPersistedEvent())
    }

    private fun buildSessionSpeakers(sessions:List<ApiSession>):Array<SessionSpeaker> {
        var sessionSpeakers = mutableListOf<SessionSpeaker>()
        sessions.forEach { session ->
            session.shortSpeakers?.forEach { speaker ->
                sessionSpeakers.add(SessionSpeaker(sessionId = session.id.toInt(), speakerId = speaker.id!!)) }
        }
        return sessionSpeakers.toTypedArray()
    }

    private fun buildRooms(apiSessions: List<ApiSession>): MutableList<ConferenceRoom> {
        var conferenceRooms = mutableListOf<ConferenceRoom>()
        apiSessions.forEach { session ->
            session.rooms?.forEach { room ->
                conferenceRooms.add(ConferenceRoom(sessionId = session.id, name = room)) }
        }
        return conferenceRooms
    }

    private fun buildTags(apiSessions: List<ApiSession>): MutableList<Tag> {
        var tags = mutableListOf<Tag>()
        apiSessions.forEach { session ->
            session.tags?.forEach { tag -> tags.add(Tag(sessionId = session.id, name = tag)) }
        }
        return tags
    }

    private fun mapApiSessionsToDomain(apiSessions: List<ApiSession>): Array<Session> {
        return apiSessions.map {
            Session(
                    Id = it.id,
                    Category = it.category,
                    SessionStartTime = it.sessionStartTime,
                    SessionEndTime = it.sessionEndTime,
                    SessionTime = it.sessionTime,
                    SessionType = it.sessionType,
                    Title = it.title,
                    Abstract = it.abstract
            )
        }.toTypedArray()
    }

    fun getSessions(): Maybe<Array<FullSession?>> {
        return conferenceDao.getSessions()
    }

    fun getSpeakers(ids: List<String>):Maybe<Array<FullSpeaker>> {
        return conferenceDao.getSpeakers(ids.toTypedArray())
    }

    fun getSessions(ids: Array<Int>): Maybe<Array<FullSession>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}