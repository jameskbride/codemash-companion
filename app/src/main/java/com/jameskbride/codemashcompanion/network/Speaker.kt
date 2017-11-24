package com.jameskbride.codemashcompanion.network

data class Speaker(
        val LinkedInProfile: String = "",
        val Id: String = "",
        val LastName: String = "",
        val SessionIds: Array<String> = arrayOf(String()),
        val TwitterLink: String = "",
        val GitHubLink: String = "",
        val FirstName: String = "",
        val GravatarUrl: String = "",
        val Biography: String = "",
        val BlogUrl: String = ""
)