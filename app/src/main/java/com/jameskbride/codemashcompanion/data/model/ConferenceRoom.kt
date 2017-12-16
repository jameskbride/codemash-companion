package com.jameskbride.codemashcompanion.data.model

import android.arch.persistence.room.Entity

@Entity(primaryKeys = arrayOf("id", "name"))
data class ConferenceRoom(val id:String, val name:String)