package com.jameskbride.codemashcompanion.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation

class FullSession constructor(
        @ColumnInfo(name = "id") @PrimaryKey var Id: String = "",

        @ColumnInfo(name = "category") var Category: String? = null,
        @ColumnInfo(name = "session_start_time") var SessionStartTime: String? = null,
        @ColumnInfo(name = "session_type") var SessionType: String? = null,
        @ColumnInfo(name = "session_end_time") var SessionEndTime: String? = null,
        @ColumnInfo(name = "session_time") var SessionTime: String? = null,
        @ColumnInfo(name = "title") var Title: String? = null,
        @ColumnInfo(name = "abstract") var Abstract: String? = null,

        @Relation(parentColumn = "id", entityColumn = "sessionId") var conferenceRooms:List<ConferenceRoom> = listOf())