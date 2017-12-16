package com.jameskbride.codemashcompanion.bus

import com.jameskbride.codemashcompanion.network.model.ApiSpeaker

data class SpeakersReceivedEvent(val speakers: List<ApiSpeaker> = listOf())