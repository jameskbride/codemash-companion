package com.jameskbride.codemashcompanion.data.model

import android.arch.persistence.room.*
import java.io.Serializable

@Entity(indices = arrayOf(
        Index(value = arrayOf("session_id"), unique = true),
        Index(value = arrayOf("speaker_id"), unique = true)
        ),
        foreignKeys = arrayOf(
                ForeignKey(entity = Session::class, parentColumns = arrayOf("id"), childColumns = arrayOf("session_id"), onDelete = ForeignKey.CASCADE),
                ForeignKey(entity = Speaker::class, parentColumns = arrayOf("id"), childColumns = arrayOf("speaker_id"), onDelete = ForeignKey.CASCADE)
        )
)
data class SessionSpeaker constructor(
        @ColumnInfo(name = "session_id") val sessionId:String,
        @ColumnInfo(name = "speaker_id") val speakerId:String,
        @PrimaryKey(autoGenerate = true) val id:Int = 0): Serializable