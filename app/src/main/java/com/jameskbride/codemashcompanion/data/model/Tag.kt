package com.jameskbride.codemashcompanion.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
data class Tag(val sessionId: String, val name: String, @PrimaryKey(autoGenerate = true) val id: Int = 0): Serializable