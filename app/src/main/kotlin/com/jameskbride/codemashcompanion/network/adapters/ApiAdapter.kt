package com.jameskbride.codemashcompanion.network.adapters

import com.jameskbride.codemashcompanion.data.model.*
import com.jameskbride.codemashcompanion.network.model.ApiSession
import com.jameskbride.codemashcompanion.network.model.ApiSpeaker
import com.jameskbride.codemashcompanion.network.model.ShortSpeaker

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
            return sessionSpeakers.flatten().filterNotNull().toList()
        }

        private fun mapApiSessionSpeakersToDomain(session: ApiSession):List<SessionSpeaker?> {
            return if (sessionHasShortSpeakers(session)) {
                session.shortSpeakers!!.map { shortSpeaker ->
                    SessionSpeaker(sessionId = session.id, speakerId = shortSpeaker.id!!)
                }
            } else { listOf() }
        }


        private fun sessionHasShortSpeakers(session: ApiSession): Boolean {
            return session.shortSpeakers != null
        }


        fun buildRooms(apiSessions: List<ApiSession>): List<ConferenceRoom> {
            return apiSessions.map { session ->
                mapApiSessionRoomsToDomain(session)
            }.filterNotNull()
        }

        private fun mapApiSessionRoomsToDomain(session: ApiSession): ConferenceRoom? {
            return  if (sessionHasARoom(session))
                ConferenceRoom(id=session.roomId!!, sessionId = session.id, name = session.room!!) else null

        }

        private fun sessionHasARoom(session: ApiSession) =
                session.roomId != null && session.room != null

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