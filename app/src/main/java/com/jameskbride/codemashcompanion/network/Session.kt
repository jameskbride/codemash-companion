package com.jameskbride.codemashcompanion.network

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity
data class Session constructor(
        @ColumnInfo(name = "id") @PrimaryKey var Id: String = "",

        @ColumnInfo(name = "category") var Category: String? = null,
        @ColumnInfo(name = "session_start_time") var SessionStartTime: String? = null,
        @ColumnInfo(name = "session_type") var SessionType: String? = null,
        @ColumnInfo(name = "session_end_time") var SessionEndTime: String? = null,
        @ColumnInfo(name = "session_time") var SessionTime: String? = null,
        @ColumnInfo(name = "title") var Title: String? = null,
        @ColumnInfo(name = "abstract") var Abstract: String? = null,

        @Ignore var speakers: Array<String>? = null,
        @Ignore var tags: Array<String>? = null
)
