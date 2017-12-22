package com.jameskbride.codemashcompanion.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation
import java.io.Serializable

class FullSession constructor(
        @ColumnInfo(name = "id") @PrimaryKey var Id: String = "",
        @ColumnInfo(name = "category") var Category: String? = null,
        @ColumnInfo(name = "session_start_time") var SessionStartTime: String? = null,
        @ColumnInfo(name = "session_type") var SessionType: String? = null,
        @ColumnInfo(name = "session_end_time") var SessionEndTime: String? = null,
        @ColumnInfo(name = "session_time") var SessionTime: String? = null,
        @ColumnInfo(name = "title") var Title: String? = null,
        @ColumnInfo(name = "abstract") var Abstract: String? = null,
        @Relation(parentColumn = "id", entityColumn = "session_id") var conferenceRooms:List<ConferenceRoom> = listOf(),
        @Relation(parentColumn = "id", entityColumn = "session_id") var tags:List<Tag> = listOf(),
        @Relation(parentColumn = "id", entityColumn = "session_id") var sessionSpeakers:List<SessionSpeaker> = listOf(),
        @Relation(parentColumn = "id", entityColumn = "session_id") var bookmarks:List<Bookmark> = listOf()
): Serializable {
    val isBookmarked: Boolean
    get() {return bookmarks.isNotEmpty()}
}