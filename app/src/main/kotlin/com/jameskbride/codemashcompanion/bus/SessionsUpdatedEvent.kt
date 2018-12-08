package com.jameskbride.codemashcompanion.bus

import com.jameskbride.codemashcompanion.data.model.Session

data class SessionsUpdatedEvent constructor(val sessions: List<Session> = listOf())