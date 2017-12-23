package com.jameskbride.codemashcompanion.data.model

import android.arch.persistence.room.*
import java.io.Serializable

@Entity(indices = arrayOf(Index(value = arrayOf("session_id"), unique = true)),
        foreignKeys = arrayOf(ForeignKey(entity = Session::class, parentColumns = arrayOf("id"), childColumns = arrayOf("session_id"), onDelete = ForeignKey.CASCADE))
)
class Bookmark constructor(
    @ColumnInfo(name = "session_id") var sessionId:String = "",
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id:Int = 0
): Serializable