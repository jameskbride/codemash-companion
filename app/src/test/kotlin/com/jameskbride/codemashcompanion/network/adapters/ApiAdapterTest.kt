package com.jameskbride.codemashcompanion.network.adapters

import com.jameskbride.codemashcompanion.network.model.ApiSpeaker
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

        assertEquals(1, speakers.size)
        assertEquals(apiSpeakers.first().id, speakers.first().Id)
        assertEquals(apiSpeakers.first().firstName, speakers.first().FirstName)
        assertEquals(apiSpeakers.first().lastName, speakers.first().LastName)
        assertEquals(apiSpeakers.first().biography, speakers.first().Biography)
    }
}