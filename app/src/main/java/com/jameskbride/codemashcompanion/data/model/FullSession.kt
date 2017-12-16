package com.jameskbride.codemashcompanion.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class FullSession constructor(@Embedded var session:Session? = null, @Relation(parentColumn = "id", entityColumn = "sessionId") var conferenceRooms:List<ConferenceRoom> = listOf())