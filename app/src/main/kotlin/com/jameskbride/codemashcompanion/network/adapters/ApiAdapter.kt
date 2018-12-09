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
                convertApiSessionsWithCollections(it)
            }
        }

        private fun convertApiSessionsWithCollections(apiSession: ApiSession): Session {
            var category = apiSession.categories?.find {it -> it.name == "Track"}?.categoryItems?.first()?.name
            return Session(
                    Id = apiSession.id,
                    Category = category,
                    SessionStartTime = apiSession.sessionStartTime,
                    SessionEndTime = apiSession.sessionEndTime,
        //                        SessionType = apiSession.sessionType,
                    Title = apiSession.title,
                    Abstract = apiSession.abstract
            )
        }

        fun buildSessionSpeakers(sessions:List<ApiSession>):List<SessionSpeaker> {
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

        fun buildTags(apiSessions: List<ApiSession>): List<Tag>? {
            var allTags:List<Tag>? = apiSessions.map { session ->
                mapApiSessionTagsToDomain(session)
            }.flatten()

            return allTags!!.toList()
        }

        private fun mapApiSessionTagsToDomain(apiSession: ApiSession):List<Tag> {
            var tagCategory = apiSession.categories?.find {it -> it.name == "Tags"}
            if (tagCategory == null) {
                return listOf()
            } else {
                return tagCategory?.categoryItems?.map { tag ->
                    Tag(sessionId = apiSession.id, name = tag.name!!)
                }
            }
        }
    }
}