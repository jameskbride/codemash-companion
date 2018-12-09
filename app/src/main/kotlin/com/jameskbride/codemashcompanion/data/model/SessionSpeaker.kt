package com.jameskbride.codemashcompanion.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(indices = arrayOf(Index(value = arrayOf("session_id", "speaker_id"), unique = true)))
data class SessionSpeaker constructor(
        @ColumnInfo(name = "session_id") val sessionId:String,
        @ColumnInfo(name = "speaker_id") val speakerId:String,
        @PrimaryKey(autoGenerate = true) val id:Int = 0): Serializable