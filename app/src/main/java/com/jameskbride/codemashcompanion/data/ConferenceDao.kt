package com.jameskbride.codemashcompanion.data

import android.arch.persistence.room.*
import com.jameskbride.codemashcompanion.data.model.*
import io.reactivex.Maybe

@Dao
interface ConferenceDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(speakers: Array<Speaker>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(sessions: Array<Session>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(rooms: Array<ConferenceRoom>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(arrayOfTags: Array<Tag>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(arrayOfSessionSpeakers: Array<SessionSpeaker>)

    @Transaction
    @Query("SELECT * FROM Speaker ORDER BY first_name IS NULL, LOWER(last_name)")
    fun getSpeakers(): Maybe<Array<FullSpeaker>>

    @Transaction
    @Query("SELECT * FROM Speaker WHERE id IN (:ids) ORDER BY first_name, LOWER(last_name)")
    fun getSpeakers(ids:Array<String>): Maybe<Array<FullSpeaker>>

    @Transaction
    @Query("SELECT * FROM Session")
    fun getSessions(): Maybe<Array<FullSession>>

    @Transaction
    @Query("SELECT * FROM Session WHERE id IN (:ids) ORDER BY title")
    fun getSessions(ids:Array<String>): Maybe<Array<FullSession>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookmark: Bookmark): Long

    @Transaction
    @Delete
    fun delete(bookmark: Bookmark)

    @Transaction
    @Query("SELECT Speaker.* FROM Speaker INNER JOIN SessionSpeaker " +
            "ON Speaker.id = SessionSpeaker.speaker_id "+
            "WHERE SessionSpeaker.session_id = :sessionId")
    fun getSpeakersBySession(sessionId: String): Maybe<Array<FullSpeaker>>

    @Transaction
    @Query("SELECT Session.* FROM Session INNER JOIN Bookmark " +
            "ON Session.id = Bookmark.session_id ")
    fun getBookmarkedSessions(): Maybe<Array<FullSession>>
}