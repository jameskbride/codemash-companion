package com.jameskbride.codemashcompanion.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import com.jameskbride.codemashcompanion.network.Speaker

@Dao
interface ConferenceDao {

    @Insert
    fun insertAll(speakers: Array<Speaker>)
}