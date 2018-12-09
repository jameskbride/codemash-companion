package com.jameskbride.codemashcompanion.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiSpeaker(
        @SerializedName("id") val id: String = "",
        @SerializedName("firstName") val firstName: String? = "",
        @SerializedName("lastName") val lastName: String? = "",
        @SerializedName("bio") val biography: String? = "",
        @SerializedName("profilePicture") val profilePicture: String? = "",
        @SerializedName("sessions") var shortSessions:List<ShortSession>? = listOf(),
        @SerializedName("links") var links:List<Link>? = listOf()
): Serializable

data class ShortSession constructor(
        @SerializedName("id") val id: String = "",
        @SerializedName("name") val firstName: String? = ""
): Serializable

data class Link constructor(
        @SerializedName("title") val title: String = "",
        @SerializedName("url") val url: String = "",
        @SerializedName("linkType") val linkType: String = ""
): Serializable