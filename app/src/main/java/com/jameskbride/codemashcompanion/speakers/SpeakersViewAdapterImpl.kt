package com.jameskbride.codemashcompanion.speakers

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.network.Speaker

class SpeakersViewAdapterImpl constructor(val context: Context) {
    private var speakers: Array<Speaker> = arrayOf()

    fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getItem(p0: Int): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getItemId(p0: Int): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getCount(): Int {
        return speakers.size
    }

    fun setSpeakers(speakers: Array<Speaker>) {
        this.speakers = speakers
    }
}