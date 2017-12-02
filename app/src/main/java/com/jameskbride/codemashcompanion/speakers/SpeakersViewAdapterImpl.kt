package com.jameskbride.codemashcompanion.speakers

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.network.Speaker

class SpeakersViewAdapterImpl constructor(val context: Context) {
    private var speakers: Array<Speaker> = arrayOf()

    fun getView(position: Int, convertView: View?, parentView: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getItem(position: Int): Any {
        return speakers[position]
    }

    fun getItemId(p0: Int): Long {
        return 0
    }

    fun getCount(): Int {
        return speakers.size
    }

    fun setSpeakers(speakers: Array<Speaker>) {
        this.speakers = speakers
    }
}