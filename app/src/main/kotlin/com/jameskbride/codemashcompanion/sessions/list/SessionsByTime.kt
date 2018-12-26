package com.jameskbride.codemashcompanion.sessions.list

import com.jameskbride.codemashcompanion.data.model.FullSession
import java.util.*

data class SessionsByTime(val sessionTime: Date, val sessions: List<FullSession> = listOf())