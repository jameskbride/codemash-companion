package com.jameskbride.codemashcompanion.sessions.list

import com.jameskbride.codemashcompanion.data.model.FullSession

data class SessionsByDate(val sessionDate: String, val sessions: List<FullSession> = listOf())