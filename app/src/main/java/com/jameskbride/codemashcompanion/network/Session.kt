package com.jameskbride.codemashcompanion.network

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity
data class Session constructor(
        @ColumnInfo(name = "id") @PrimaryKey var id: String = "",

        @ColumnInfo(name = "category") var category: String? = null,
        @ColumnInfo(name = "session_start_time") var sessionStartTime: String? = null,
        @ColumnInfo(name = "session_type") var sessionType: String? = null,
        @ColumnInfo(name = "session_end_time") var sessionEndTime: String? = null,
        @ColumnInfo(name = "session_time") var sessionTime: String? = null,
        @ColumnInfo(name = "title") var title: String? = null,
        @ColumnInfo(name = "abstract") var abstract: String? = null,

        @Ignore var speakers: Array<String>? = null,
        @Ignore var tags: Array<String>? = null
)
