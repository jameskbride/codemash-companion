package com.jameskbride.codemashcompanion.speakers.detail

import com.jameskbride.codemashcompanion.data.model.FullSpeaker
import java.io.Serializable

data class SpeakersParams(val speakers:Array<FullSpeaker> = arrayOf(), val index:Int): Serializable