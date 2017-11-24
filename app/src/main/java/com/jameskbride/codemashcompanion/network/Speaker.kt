package com.jameskbride.codemashcompanion.network

data class Speaker(
        val linkedInProfile: String = "",
        val id: String = "",
        val lastName: String = "",
        val sessionIds: Array<String> = arrayOf(String()),
        val twitterLink: String = "",
        val gitHubLink: String = "",
        val firstName: String = "",
        val gravatarUrl: String = "",
        val biography: String = "",
        val blogUrl: String = ""
)