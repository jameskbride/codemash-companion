package com.jameskbride.codemashcompanion.bus

import com.jameskbride.codemashcompanion.data.model.ConferenceRoom

data class RoomsUpdatedEvent constructor(val conferenceRooms: List<ConferenceRoom> = listOf())