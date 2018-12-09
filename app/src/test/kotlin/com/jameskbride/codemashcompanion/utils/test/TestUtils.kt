package com.jameskbride.codemashcompanion.utils.test

import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.data.model.Session
import com.jameskbride.codemashcompanion.data.model.Speaker
import com.jameskbride.codemashcompanion.network.model.ApiSpeaker
import com.jameskbride.codemashcompanion.network.model.Link

fun buildDefaultSpeakers(count:Int = 1): Array<Speaker> {

    val speakers = mutableListOf<Speaker>()
    for (i in 0 until count) {
        speakers.add(Speaker(
                LinkedInProfile = "linkedin",
                Id = "1234",
                LastName = "Smith",
                TwitterLink = "twitter",
                GitHubLink = "github",
                FirstName = "John",
                GravatarUrl = "gravitar",
                Biography = "biography",
                BlogUrl = "blog"
        ))
    }

    return speakers.toTypedArray()
}

fun buildDefaultFullSpeakers(count:Int = 1): Array<FullSpeaker> {

    val speakers = mutableListOf<FullSpeaker>()
    for (i in 0 until count) {
        speakers.add(FullSpeaker(
                LinkedInProfile = "linkedin",
                Id = "1234",
                LastName = "Smith",
                TwitterLink = "twitter",
                GitHubLink = "github",
                FirstName = "John",
                GravatarUrl = "gravitar",
                Biography = "biography",
                BlogUrl = "blog"
        ))
    }

    return speakers.toTypedArray()
}

fun buildDefaultApiSpeakers(count:Int = 1): Array<ApiSpeaker> {

    val speakers = mutableListOf<ApiSpeaker>()
    for (i in 0 until count) {
        speakers.add(ApiSpeaker(
                id = "1234",
                lastName = "Smith",
                firstName = "John",
                biography = "biography",
                links = listOf(
                        Link(title = "LinkedIn", url = "linkedin", linkType = "LinkedIn"),
                        Link(title = "Twitter", url = "twitter", linkType = "Twitter"),
                        Link(title = "Blog", url = "blog", linkType = "Blog")
                )
        ))
    }

    return speakers.toTypedArray()
}

fun buildDefaultSessions(count:Int = 1): Array<Session> {
    val sessions = mutableListOf<Session>()
    for (i in 0 until count) {
        sessions.add(Session(
            Id = "$i",
            Category = "some category",
            SessionStartTime = "",
            SessionType = "some type",
            SessionEndTime = "",
            SessionTime = "",
            Title = "some title",
            Abstract = "some abstract")
        )
    }

    return sessions.toTypedArray()
}