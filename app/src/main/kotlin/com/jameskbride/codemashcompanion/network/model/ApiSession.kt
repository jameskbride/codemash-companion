package com.jameskbride.codemashcompanion.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiSession constructor(
        @SerializedName("id") var id: String = "",
        @SerializedName("startsAt") var sessionStartTime: String? = "2019-01-08T08:00:00",
        @SerializedName("endsAt") var sessionEndTime: String? = "2019-01-08T12:00:00",
        @SerializedName("title") var title: String? = "",
        @SerializedName("description") var abstract: String? = "",
        @SerializedName("roomId") var roomId:Int? = -1,
        @SerializedName("room") var room:String? = "",
        @SerializedName("categories") var categories:List<Category>? = listOf(),
        @SerializedName("speakers") var shortSpeakers:List<ShortSpeaker>? = listOf()
): Serializable

data class ShortSpeaker constructor(
        @SerializedName("id") var id:String? = ""
): Serializable

data class Category constructor(
        @SerializedName("id") var id:Int? = -1,
        @SerializedName("name") var name:String? = "",
        @SerializedName("categoryItems") var categoryItems:List<CategoryItem> = listOf()
)

data class CategoryItem constructor(
        @SerializedName("id") var id:Int? = -1,
        @SerializedName("name") var name:String? = ""
)