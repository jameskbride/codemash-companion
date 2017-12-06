package com.jameskbride.codemashcompanion.speakers.detail

import com.jameskbride.codemashcompanion.network.Speaker
import java.io.Serializable

data class SpeakerDetailParams(val speakers:Array<Speaker> = arrayOf(), val index:Int): Serializable