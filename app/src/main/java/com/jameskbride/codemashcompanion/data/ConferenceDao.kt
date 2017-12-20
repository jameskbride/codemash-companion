package com.jameskbride.codemashcompanion.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.jameskbride.codemashcompanion.data.model.*
import io.reactivex.Maybe

@Dao
interface ConferenceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(speakers: Array<Speaker>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(sessions: Array<Session>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(rooms: Array<ConferenceRoom>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(arrayOfTags: Array<Tag>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(arrayOfSessionSpeakers: Array<SessionSpeaker>)

    @Query("SELECT * FROM Speaker ORDER BY first_name IS NULL")
    fun getSpeakers(): Maybe<Array<FullSpeaker>>

    @Query("SELECT * FROM Speaker WHERE id IN (:ids) ORDER BY first_name")
    fun getSpeakers(ids:Array<String>): Maybe<Array<FullSpeaker>>

    @Query("SELECT * FROM Session")
    fun getSessions(): Maybe<Array<FullSession>>

    @Query("SELECT * FROM Session WHERE id IN (:ids) ORDER BY title")
    fun getSessions(ids:Array<Int>): Maybe<Array<FullSession>>
}