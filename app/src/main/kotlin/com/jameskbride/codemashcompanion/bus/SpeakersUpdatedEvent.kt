package com.jameskbride.codemashcompanion.bus

import com.jameskbride.codemashcompanion.data.model.Speaker

data class SpeakersUpdatedEvent(val speakers: List<Speaker> = listOf())