package com.jameskbride.codemashcompanion.network.adapters

import com.jameskbride.codemashcompanion.network.adapters.ApiAdapter.Companion.buildRooms
import com.jameskbride.codemashcompanion.network.adapters.ApiAdapter.Companion.mapApiSessionsToDomain
import com.jameskbride.codemashcompanion.network.model.*
import org.junit.Assert.assertEquals
import org.junit.Test

class ApiAdapterTest {

    @Test
    fun mapApiSpeakersToDomainConvertsBasicFields() {
        val apiSpeakers = listOf(ApiSpeaker(
                id = "1234",
                firstName = "first name",
                lastName = "last name",
                biography = "some biography"
        ))

        val speakers = ApiAdapter.mapApiSpeakersToDomain(apiSpeakers)

        assertEquals(apiSpeakers.first().id, speakers.first().Id)
        assertEquals(apiSpeakers.first().firstName, speakers.first().FirstName)
        assertEquals(apiSpeakers.first().lastName, speakers.first().LastName)
        assertEquals(apiSpeakers.first().biography, speakers.first().Biography)
    }

    @Test
    fun mapApiSpeakersToDomainConvertsTheProfilePicture() {
        val apiSpeakers = listOf(ApiSpeaker(
                id = "1234",
                profilePicture = "some url"
        ))

        val speakers = ApiAdapter.mapApiSpeakersToDomain(apiSpeakers)

        assertEquals(apiSpeakers.first().profilePicture, speakers.first().GravatarUrl)
    }

    @Test
    fun mapApiSpeakersToDomainConvertsTheTwitterLink() {
        val twitterLink = Link("Twitter", "twitter url", linkType = "Twitter")
        val apiSpeakers = listOf(ApiSpeaker(
                id = "1234",
                profilePicture = "some url",
                links = listOf(
                    twitterLink
                )
        ))

        val speakers = ApiAdapter.mapApiSpeakersToDomain(apiSpeakers)

        assertEquals(twitterLink.url, speakers.first().TwitterLink)
    }

    @Test
    fun mapApiSpeakersToDomainConvertsTheBlogLink() {
        val blogLink = Link("Blog", "blog url", linkType = "Blog")
        val apiSpeakers = listOf(ApiSpeaker(
                id = "1234",
                profilePicture = "some url",
                links = listOf(
                        blogLink
                )
        ))

        val speakers = ApiAdapter.mapApiSpeakersToDomain(apiSpeakers)

        assertEquals(blogLink.url, speakers.first().BlogUrl)
    }

    @Test
    fun mapApiSpeakersToDomainConvertsTheLinkedInLink() {
        val linkedInLink = Link("LinkedIn", "linked url", linkType = "LinkedIn")
        val apiSpeakers = listOf(ApiSpeaker(
                id = "1234",
                profilePicture = "some url",
                links = listOf(
                        linkedInLink
                )
        ))

        val speakers = ApiAdapter.mapApiSpeakersToDomain(apiSpeakers)

        assertEquals(linkedInLink.url, speakers.first().LinkedInProfile)
    }

    @Test
    fun mapApiSessionsToDomainConvertsBasicFields() {
        val apiSession = ApiSession(
                id = "1234",
                sessionStartTime = "some start time",
                sessionEndTime = "some end time",
                title = "title",
                abstract = "abstract"
        )
        val apiSessions = listOf(
            apiSession
        )

        val sessions = mapApiSessionsToDomain(apiSessions)

        assertEquals(apiSession.id, sessions.first().Id)
        assertEquals(apiSession.sessionStartTime, sessions.first().SessionStartTime)
        assertEquals(apiSession.sessionEndTime, sessions.first().SessionEndTime)
        assertEquals(apiSession.title, sessions.first().Title)
        assertEquals(apiSession.abstract, sessions.first().Abstract)
    }

    @Test
    fun mapApiSessionsToDomainConvertsTrackToCategoryType() {
        val trackCategory = Category(
                id = 1234,
                name = "Track",
                categoryItems = listOf(CategoryItem(1234, name = "DevOps")))
        val apiSession = ApiSession(
                id = "1234",
                sessionStartTime = "some start time",
                sessionEndTime = "some end time",
                title = "title",
                abstract = "abstract",
                categories = listOf(trackCategory)
        )

        val sessions = mapApiSessionsToDomain(listOf(apiSession))

        assertEquals("DevOps", sessions.first().Category)
    }
    
    @Test
    fun buildRoomsConvertsTheRooms() {
        val apiSession = ApiSession(
                id = "1234",
                sessionStartTime = "some start time",
                sessionEndTime = "some end time",
                title = "title",
                abstract = "abstract",
                roomId = 1234,
                room = "some room"
        )

        val rooms = buildRooms(listOf(apiSession))

        assertEquals("some room", rooms.first().name)
        assertEquals("1234", rooms.first().sessionId)
    }
}