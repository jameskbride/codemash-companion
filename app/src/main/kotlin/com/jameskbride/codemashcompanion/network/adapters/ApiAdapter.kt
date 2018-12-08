package com.jameskbride.codemashcompanion.network.adapters

import com.jameskbride.codemashcompanion.data.model.*
import com.jameskbride.codemashcompanion.network.model.ApiSession
import com.jameskbride.codemashcompanion.network.model.ApiSpeaker

class ApiAdapter {

    companion object {
        fun mapApiSpeakersToDomain(apiSpeakers: List<ApiSpeaker>): Array<Speaker> {
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

        fun mapApiSessionSpeakersToDomain(session: ApiSession) =
                session.shortSpeakers!!.map { speaker ->
                    SessionSpeaker(sessionId = session.id.toString(), speakerId = speaker.id!!)
                }

        fun mapApiSessionRoomsToDomain(session: ApiSession) =
                session.rooms!!.map { room ->
                    ConferenceRoom(sessionId = session.id.toString(), name = room)
                }

        fun mapApiSessionTagsToDomain(session: ApiSession) =
                session.tags!!.map { tag -> Tag(sessionId = session.id.toString(), name = tag) }

        fun mapApiSessionsToDomain(apiSessions: List<ApiSession>): Array<Session> {
            return apiSessions.map {
                Session(
                        Id = it.id.toString(),
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

        fun buildSessionSpeakers(sessions:List<ApiSession>):Array<SessionSpeaker> {
            val sessionSpeakers = sessions.map { session ->
                mapApiSessionSpeakersToDomain(session)
            }
            return sessionSpeakers.flatten().toTypedArray()
        }

        fun buildRooms(apiSessions: List<ApiSession>): MutableList<ConferenceRoom> {
            val allRooms = apiSessions.map { session ->
                mapApiSessionRoomsToDomain(session)
            }
            return allRooms.flatten().toMutableList()
        }

        fun buildTags(apiSessions: List<ApiSession>): MutableList<Tag> {
            val allTags = apiSessions.map { session ->
                mapApiSessionTagsToDomain(session)
            }

            return allTags.flatten().toMutableList()
        }
    }
}