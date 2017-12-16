package com.jameskbride.codemashcompanion.data

import com.jameskbride.codemashcompanion.bus.*
import com.jameskbride.codemashcompanion.data.model.ConferenceRoom
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.Session
import com.jameskbride.codemashcompanion.data.model.Speaker
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

    fun getSpeakers(): Maybe<Array<Speaker>> {
        return conferenceDao.getSpeakers()
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onSessionsReceivedEvent(sessionsReceivedEvent: SessionsReceivedEvent) {
        val apiSessions = sessionsReceivedEvent.sessions
        val sessions = mapApiSessionsToDomain(apiSessions)
        conferenceDao.insertAll(sessions)

        var conferenceRooms = buildRooms(apiSessions)
        conferenceDao.insertAll(conferenceRooms.toTypedArray())
        eventBus.post(ConferenceDataPersistedEvent())
    }

    private fun buildRooms(apiSessions: List<ApiSession>): MutableList<ConferenceRoom> {
        var conferenceRooms = mutableListOf<ConferenceRoom>()
        apiSessions.forEach { session ->
            session.rooms?.forEach { room -> conferenceRooms.add(ConferenceRoom(session.id, room)) }
        }
        return conferenceRooms
    }

    private fun mapApiSessionsToDomain(apiSessions: List<ApiSession>): Array<Session> {
        val sessions = apiSessions.map {
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
        return sessions
    }

    fun getSessions(): Maybe<Array<FullSession?>> {
        return conferenceDao.getSessions()
    }
}