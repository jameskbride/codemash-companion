package com.jameskbride.codemashcompanion.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(indices = arrayOf(Index(value = arrayOf("session_id", "name"), unique = true)))
data class ConferenceRoom(
        @ColumnInfo(name = "session_id") val sessionId:String,
        @ColumnInfo(name = "name") val name:String,
        @PrimaryKey(autoGenerate = true) val id:Int = 0): Serializable