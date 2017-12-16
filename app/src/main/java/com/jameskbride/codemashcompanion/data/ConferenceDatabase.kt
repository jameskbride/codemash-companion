package com.jameskbride.codemashcompanion.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.jameskbride.codemashcompanion.data.model.ConferenceRoom
import com.jameskbride.codemashcompanion.data.model.Session
import com.jameskbride.codemashcompanion.data.model.Speaker

@Database(entities = arrayOf(Speaker::class, Session::class, ConferenceRoom::class), version = 1)
abstract class  ConferenceDatabase : RoomDatabase() {

    abstract fun conferenceDao(): ConferenceDao

}