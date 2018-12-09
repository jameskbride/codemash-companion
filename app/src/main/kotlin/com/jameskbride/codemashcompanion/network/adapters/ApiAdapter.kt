package com.jameskbride.codemashcompanion.network.adapters

import com.jameskbride.codemashcompanion.data.model.*
import com.jameskbride.codemashcompanion.network.model.ApiSession
import com.jameskbride.codemashcompanion.network.model.ApiSpeaker

class ApiAdapter {

    companion object {
        fun mapApiSpeakersToDomain(apiSpeakers: List<ApiSpeaker>): List<Speaker> {
            return apiSpeakers.map { convertApiSpeakerWithCollections(it) }
        }

        private fun convertApiSpeakerWithCollections(apiSpeaker: ApiSpeaker): Speaker {
            return Speaker(
                    Id = apiSpeaker.id,
                    FirstName = apiSpeaker.firstName,
                    LastName = apiSpeaker.lastName,
                    LinkedInProfile = findLinkByType(apiSpeaker, "LinkedIn"),
                    TwitterLink = findLinkByType(apiSpeaker, "Twitter"),
                    BlogUrl = findLinkByType(apiSpeaker, "Blog"),
                    GravatarUrl = apiSpeaker.profilePicture,
                    Biography = apiSpeaker.biography
            )
        }

        private fun findLinkByType(apiSpeaker: ApiSpeaker, type:String) =
                apiSpeaker.links?.find { it -> it.linkType == type }?.url ?: null

        fun mapApiSessionsToDomain(apiSessions: List<ApiSession>): List<Session> {
            return apiSessions.map {
                Session(
                        Id = it.id,
//                        Category = it.category,
                        SessionStartTime = it.sessionStartTime,
                        SessionEndTime = it.sessionEndTime,
//                        SessionType = it.sessionType,
                        Title = it.title,
                        Abstract = it.abstract
                )
            }
        }

        fun buildSessionSpeakers(sessions:List<ApiSession>):MutableList<SessionSpeaker> {
            val sessionSpeakers = sessions.map { session ->
                mapApiSessionSpeakersToDomain(session)
            }
            return sessionSpeakers.flatten().toMutableList()
        }

        private fun mapApiSessionSpeakersToDomain(session: ApiSession) =
                session.shortSpeakers!!.map { speaker ->
                    SessionSpeaker(sessionId = session.id, speakerId = speaker.id!!)
                }

        fun buildRooms(apiSessions: List<ApiSession>): List<ConferenceRoom> {
            return apiSessions.map { session ->
                mapApiSessionRoomsToDomain(session)
            }
        }

        private fun mapApiSessionRoomsToDomain(session: ApiSession): ConferenceRoom {
            return ConferenceRoom(id=session.roomId!!, sessionId = session.id, name = session.room!!)
        }

        fun buildTags(apiSessions: List<ApiSession>): MutableList<Tag> {
            val allTags = apiSessions.map { session ->
                mapApiSessionTagsToDomain(session)
            }

            return allTags.flatten().toMutableList()
        }

        private fun mapApiSessionTagsToDomain(session: ApiSession):List<Tag> = listOf()
//                session.tags!!.map { tag -> Tag(sessionId = session.id, name = tag) }
    }
}