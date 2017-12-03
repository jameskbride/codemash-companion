package com.jameskbride.codemashcompanion.speakers

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.network.Speaker
import com.jameskbride.codemashcompanion.utils.PicassoWrapper

class SpeakersViewAdapterImpl constructor(val context: Context,
                                          val picassoWrapper: PicassoWrapper = PicassoWrapper()) {
    private var speakers: Array<Speaker> = arrayOf()

    fun getView(position: Int, convertView: View?, parentView: ViewGroup?,
                speakerViewAdapter: SpeakersViewAdapter): View {

        val imageView = speakerViewAdapter.buildImageView()
        picassoWrapper.with(context).load(speakers[position].GravatarUrl).into(imageView)

        return imageView
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