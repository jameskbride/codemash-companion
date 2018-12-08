package com.jameskbride.codemashcompanion.bus

import com.jameskbride.codemashcompanion.data.model.SessionSpeaker

data class SessionSpeakersUpdatedEvent constructor(val sessionSpeakers: List<SessionSpeaker> = listOf())