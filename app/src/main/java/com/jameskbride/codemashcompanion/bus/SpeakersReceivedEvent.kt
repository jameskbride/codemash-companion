package com.jameskbride.codemashcompanion.bus

import com.jameskbride.codemashcompanion.network.Speaker
import java.util.*

data class SpeakersReceivedEvent(val speakers: Array<Speaker> = arrayOf(Speaker()))