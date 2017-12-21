package com.jameskbride.codemashcompanion.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(indices = arrayOf(Index(value = arrayOf("session_id", "name"), unique = true)))
data class ConferenceRoom(
        @ColumnInfo(name = "session_id") val sessionId:String,
        @ColumnInfo(name = "name") val name:String,
        @PrimaryKey(autoGenerate = true) val id:Int = 0): Serializable