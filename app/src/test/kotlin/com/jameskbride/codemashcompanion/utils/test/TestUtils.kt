package com.jameskbride.codemashcompanion.utils.test

import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import com.jameskbride.codemashcompanion.data.model.Session
import com.jameskbride.codemashcompanion.data.model.Speaker
import com.jameskbride.codemashcompanion.network.model.ApiSpeaker

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
                linkedInProfile = "linkedin",
                lastName = "Smith",
                twitterLink = "twitter",
                gitHubLink = "github",
                firstName = "John",
                gravatarUrl = "gravatar",
                biography = "biography",
                blogUrl = "blog",
                sessionIds = listOf("1", "2")
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