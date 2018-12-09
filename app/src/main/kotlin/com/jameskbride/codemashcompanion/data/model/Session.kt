package com.jameskbride.codemashcompanion.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Session constructor(
        @ColumnInfo(name = "id") @PrimaryKey var Id: String = "",

        @ColumnInfo(name = "category") var Category: String? = null,
        @ColumnInfo(name = "session_start_time") var SessionStartTime: String? = null,
        @ColumnInfo(name = "session_type") var SessionType: String? = null,
        @ColumnInfo(name = "session_end_time") var SessionEndTime: String? = null,
        @ColumnInfo(name = "session_time") var SessionTime: String? = null,
        @ColumnInfo(name = "title") var Title: String? = null,
        @ColumnInfo(name = "abstract") var Abstract: String? = null) {
    companion object {
        val TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm"
        val SHORT_DATE_FORMAT =  "M/d/yyyy"
    }
}
