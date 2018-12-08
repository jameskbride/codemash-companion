package com.jameskbride.codemashcompanion.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiSession constructor(
        @SerializedName("Id") var id: Int,
        @SerializedName("Category") var category: String? = "",
        @SerializedName("SessionStartTime") var sessionStartTime: String? = "",
        @SerializedName("SessionType") var sessionType: String? = "",
        @SerializedName("SessionEndTime") var sessionEndTime: String? = "",
        @SerializedName("SessionTime") var sessionTime: String? = "",
        @SerializedName("Title") var title: String? = "",
        @SerializedName("Abstract") var abstract: String? = "",
        @SerializedName("Tags") var tags:List<String>? = listOf(),
        @SerializedName("Room") var room:String? = "",
        @SerializedName("Rooms") var rooms:List<String>? = listOf(),
        @SerializedName("Speakers") var shortSpeakers:List<ShortSpeaker>? = listOf()
): Serializable

data class ShortSpeaker constructor(
        @SerializedName("Id") var id:String? = "",
        @SerializedName("FirstName") var firstName:String? = "",
        @SerializedName("LastName") var lastName:String =  "",
        @SerializedName("GravatarUrl") var gravatarUrl:String? = ""
): Serializable
