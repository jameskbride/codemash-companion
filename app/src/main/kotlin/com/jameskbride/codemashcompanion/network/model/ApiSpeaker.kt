package com.jameskbride.codemashcompanion.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiSpeaker(
        @SerializedName("Id") val id: String = "",
        @SerializedName("LinkedInProfile") val linkedInProfile: String? = "",
        @SerializedName("LastName") val lastName: String? = "",
        @SerializedName("TwitterLink") val twitterLink: String? = "",
        @SerializedName("GitHubLink") val gitHubLink: String? = "",
        @SerializedName("FirstName") val firstName: String? = "",
        @SerializedName("GravatarUrl") val gravatarUrl: String? = "",
        @SerializedName("Biography") val biography: String? = "",
        @SerializedName("BlogUrl") val blogUrl: String? = "",
        @SerializedName("SessionIds") val sessionIds:List<String> = listOf()
): Serializable