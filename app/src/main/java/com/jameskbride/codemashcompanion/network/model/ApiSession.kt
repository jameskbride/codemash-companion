package com.jameskbride.codemashcompanion.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiSession constructor(
        @SerializedName("Id") val id: String = "",
        @SerializedName("Category") val category: String? = "",
        @SerializedName("SessionStartTime") val sessionStartTime: String? = "",
        @SerializedName("SessionType") val sessionType: String? = "",
        @SerializedName("SessionEndTime") val sessionEndTime: String? = "",
        @SerializedName("SessionTime") val sessionTime: String? = "",
        @SerializedName("Title") val title: String? = "",
        @SerializedName("Abstract") val abstract: String? = "",
        @SerializedName("Tags") val tags:List<String> = listOf(),
        @SerializedName("Room") val room:String = "",
        @SerializedName("Rooms") val rooms:List<String> = listOf(),
        @SerializedName("Speakers") val shortSpeakers:List<ShortSpeaker> = listOf()
): Serializable

data class ShortSpeaker constructor(
        @SerializedName("Id") val id:String = "",
        @SerializedName("FirstName") val firstName:String = "",
        @SerializedName("LastName") val lastName:String =  "",
        @SerializedName("GravatarUrl") val gravatarUrl:String = ""
): Serializable
