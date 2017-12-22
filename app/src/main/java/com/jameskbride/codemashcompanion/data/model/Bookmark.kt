package com.jameskbride.codemashcompanion.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(indices = arrayOf(Index(value = arrayOf("session_id"), unique = true)))
class Bookmark constructor(
    @ColumnInfo(name = "session_id") var sessionId:String = "",
    @ColumnInfo(name = "id") @PrimaryKey var id:Int = 0
): Serializable