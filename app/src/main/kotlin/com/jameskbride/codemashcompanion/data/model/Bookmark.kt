package com.jameskbride.codemashcompanion.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(indices = arrayOf(Index(value = arrayOf("session_id"), unique = true)))
class Bookmark constructor(
    @ColumnInfo(name = "session_id") var sessionId:String = "",
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id:Int = 0
): Serializable