package com.jameskbride.codemashcompanion.utils.test

import com.jameskbride.codemashcompanion.network.Speaker

fun buildDefaultSpeakers(): Array<Speaker> {
    return arrayOf(Speaker(
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