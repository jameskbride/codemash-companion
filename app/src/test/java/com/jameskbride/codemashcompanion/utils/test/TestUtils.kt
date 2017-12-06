package com.jameskbride.codemashcompanion.utils.test

import com.jameskbride.codemashcompanion.network.Speaker

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