package com.jameskbride.codemashcompanion.bus

import com.jameskbride.codemashcompanion.network.Speaker

data class SpeakersReceivedEvent(val speakers: Array<Speaker> = arrayOf(Speaker()))