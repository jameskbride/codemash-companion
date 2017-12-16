package com.jameskbride.codemashcompanion.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class ConferenceRoom(val sessionId:String, val name:String, @PrimaryKey(autoGenerate = true) val id:Int = 0)