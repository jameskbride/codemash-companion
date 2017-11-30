package com.jameskbride.codemashcompanion.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.jameskbride.codemashcompanion.network.Session
import com.jameskbride.codemashcompanion.network.Speaker
import io.reactivex.Maybe

@Dao
interface ConferenceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(speakers: Array<Speaker>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(sessions: Array<Session>)

    @Query("SELECT * FROM Speaker ORDER BY first_name")
    fun getSpeakers(): Maybe<Array<Speaker>>
}