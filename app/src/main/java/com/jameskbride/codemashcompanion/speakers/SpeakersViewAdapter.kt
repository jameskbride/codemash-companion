package com.jameskbride.codemashcompanion.speakers

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.jameskbride.codemashcompanion.network.Speaker

class SpeakersViewAdapter(val context: Context, val impl: SpeakersViewAdapterImpl = SpeakersViewAdapterImpl(context)) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        return impl.getView(p0, p1, p2, this)
    }

    override fun getItem(p0: Int): Any {
        return impl.getItem(p0)
    }

    override fun getItemId(p0: Int): Long {
        return impl.getItemId(p0)
    }

    override fun getCount(): Int {
        return impl.getCount()
    }

    fun setSpeakers(speakers: Array<Speaker>) {
        impl.setSpeakers(speakers)
    }

    fun buildImageView(): ImageView {
        return ImageView(context)
    }
}

class SpeakersViewAdapterFactory {
    fun make(context:Context): SpeakersViewAdapter {
        return SpeakersViewAdapter(context)
    }
}