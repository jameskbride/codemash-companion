package com.jameskbride.codemashcompanion.network.adapters

import com.jameskbride.codemashcompanion.network.model.ApiSpeaker
import com.jameskbride.codemashcompanion.network.model.Link
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
}