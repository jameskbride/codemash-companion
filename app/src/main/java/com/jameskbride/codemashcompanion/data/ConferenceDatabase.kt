package com.jameskbride.codemashcompanion.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.jameskbride.codemashcompanion.data.model.*

@Database(entities = arrayOf(Speaker::class, Session::class, ConferenceRoom::class, Tag::class, SessionSpeaker::class), version = 1)
abstract class  ConferenceDatabase : RoomDatabase() {

    abstract fun conferenceDao(): ConferenceDao

}