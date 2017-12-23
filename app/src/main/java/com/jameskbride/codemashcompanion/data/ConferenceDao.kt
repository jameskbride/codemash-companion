package com.jameskbride.codemashcompanion.data

import android.arch.persistence.room.*
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
    fun getSessions(ids:Array<String>): Maybe<Array<FullSession>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookmark: Bookmark): Long

    @Delete
    fun delete(bookmark: Bookmark)

    @Query("SELECT * FROM Speaker INNER JOIN SessionSpeaker " +
            "ON Speaker.id = SessionSpeaker.speaker_id "+
            "WHERE SessionSpeaker.session_id = :sessionId")
    fun getSpeakersBySession(sessionId: String): Maybe<Array<FullSpeaker>>
}