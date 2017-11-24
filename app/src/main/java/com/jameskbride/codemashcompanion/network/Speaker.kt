package com.jameskbride.codemashcompanion.network

data class Speaker(
        var linkedInProfile: String? = null,
        var id: String? = null,
        var lastName: String? = null,
        var sessionIds: Array<String> = arrayOf(String()),
        var twitterLink: String? = null,
        var gitHubLink: String? = null,
        var firstName: String? = null,
        var gravatarUrl: String? = null,
        var biography: String? = null,
        var blogUrl: String? = null
)