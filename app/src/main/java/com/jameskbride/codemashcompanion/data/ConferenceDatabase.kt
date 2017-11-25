package com.jameskbride.codemashcompanion.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.jameskbride.codemashcompanion.network.Speaker

@Database(entities = arrayOf(Speaker::class), version = 1)
abstract class  ConferenceDatabase : RoomDatabase() {

    abstract fun conferenceDao(): ConferenceDao

}