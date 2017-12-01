package com.jameskbride.codemashcompanion.speakers

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.jameskbride.codemashcompanion.network.Speaker

class SpeakersViewAdapter(val context: Context) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItem(p0: Int): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemId(p0: Int): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setSpeakers(speakers: Array<Speaker>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class SpeakersViewAdapterFactory {
    fun make(context:Context): SpeakersViewAdapter {
        return SpeakersViewAdapter(context)
    }
}