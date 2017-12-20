package com.jameskbride.codemashcompanion.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation
import java.io.Serializable

class FullSpeaker constructor(
        @ColumnInfo(name = "id") @PrimaryKey
        var Id: String = "",
        @ColumnInfo(name = "first_name") var FirstName: String? = "",
        @ColumnInfo(name = "last_name") var LastName: String? = "",
        @ColumnInfo(name = "linkedin_profile") var LinkedInProfile: String? = "",
        @ColumnInfo(name = "twitter_link") var TwitterLink: String? = "",
        @ColumnInfo(name = "github_link") var GitHubLink: String? = "",
        @ColumnInfo(name = "gravatar_url") var GravatarUrl: String? = "",
        @ColumnInfo(name = "biography") var Biography: String? = "",
        @ColumnInfo(name = "blog_url") var BlogUrl: String? = "",
        @Relation(parentColumn = "id", entityColumn = "speakerId") var sessionSpeakers:List<SessionSpeaker> = listOf()
):Serializable