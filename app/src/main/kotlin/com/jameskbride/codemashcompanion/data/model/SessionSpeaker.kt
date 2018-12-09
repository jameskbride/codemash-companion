package com.jameskbride.codemashcompanion.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(indices = arrayOf(Index(value = arrayOf("session_id", "speaker_id"), unique = true)))
data class SessionSpeaker constructor(
        @ColumnInfo(name = "session_id") val sessionId:String,
        @ColumnInfo(name = "speaker_id") val speakerId:String,
        @PrimaryKey(autoGenerate = true) val id:Int = 0): Serializable