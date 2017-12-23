package com.jameskbride.codemashcompanion.data.model

import android.arch.persistence.room.*
import java.io.Serializable

@Entity(indices = arrayOf(Index(value = arrayOf("session_id", "name"), unique = true)),
        foreignKeys = arrayOf(ForeignKey(entity = Session::class, parentColumns = arrayOf("id"), childColumns = arrayOf("session_id"), onDelete = ForeignKey.CASCADE))
)
data class ConferenceRoom(
        @ColumnInfo(name = "session_id") val sessionId:String,
        @ColumnInfo(name = "name") val name:String,
        @PrimaryKey(autoGenerate = true) val id:Int = 0): Serializable