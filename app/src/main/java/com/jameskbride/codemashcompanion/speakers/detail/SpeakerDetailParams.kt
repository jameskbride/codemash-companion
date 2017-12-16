package com.jameskbride.codemashcompanion.speakers.detail

import com.jameskbride.codemashcompanion.data.model.Speaker
import java.io.Serializable

data class SpeakerDetailParams(val speakers:Array<Speaker> = arrayOf(), val index:Int): Serializable