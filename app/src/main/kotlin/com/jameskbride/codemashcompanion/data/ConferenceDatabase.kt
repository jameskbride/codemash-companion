package com.jameskbride.codemashcompanion.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jameskbride.codemashcompanion.data.model.*

@Database(entities = arrayOf(
        Speaker::class,
        Session::class,
        ConferenceRoom::class,
        Tag::class,
        SessionSpeaker::class,
        Bookmark::class), version = 1)
abstract class  ConferenceDatabase : RoomDatabase() {

    abstract fun conferenceDao(): ConferenceDao

}